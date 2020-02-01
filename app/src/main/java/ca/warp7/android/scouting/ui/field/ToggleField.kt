package ca.warp7.android.scouting.ui.field

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import ca.warp7.android.scouting.R
import ca.warp7.android.scouting.entry.DataPoint
import ca.warp7.android.scouting.ui.toggle.ToggleSwitchCompat

/*
https://github.com/llollox/Android-Toggle-Switch
 */
class ToggleField : LinearLayout, BaseFieldWidget {

    override val fieldData: FieldData?

    private val almostWhite = ContextCompat.getColor(context, R.color.colorAlmostWhite)
    private val almostBlack = ContextCompat.getColor(context, R.color.colorAlmostBlack)
    private val accent = ContextCompat.getColor(context, R.color.colorAccent)

    private val toggleSwitch: ToggleSwitchCompat?
    private var checkedPosition = -1
    private var defaultPosition = 0

    constructor(context: Context) : super(context) {
        fieldData = null
        toggleSwitch = null
    }

    private fun getToggleButtonTextSize(): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, context.resources.displayMetrics)
    }

    internal constructor(data: FieldData) : super(data.context) {
        fieldData = data
        orientation = VERTICAL

        setBackgroundResource(R.drawable.layer_list_bg_group)
        background.mutate()
        gravity = Gravity.CENTER

        TextView(data.context).apply {
            text = data.modifiedName
            setTextColor(almostBlack)
            textSize = 14f
            setPadding(0, 8, 0, 0)
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            addView(this)
        }

        // parse the default options
        val options = mutableListOf<String>()
        data.templateField.options?.forEachIndexed { i, v ->
            if (v.startsWith("default:")) {
                defaultPosition = i
                options.add(v.substring(8))
            } else options.add(v)
        }

        toggleSwitch = ToggleSwitchCompat(data.context).apply {
            checkedBackgroundColor = accent
            uncheckedBackgroundColor = almostWhite
            textSize = getToggleButtonTextSize()
            uncheckedTextColor = accent
            separatorVisible = false
            elevation = 4f

            setEntries(options)

            layoutHeight = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT

            setPadding(8, 4, 8, 8)
            setOnChangeListener { index -> onToggle(data, index) }

        }.also { addView(it) }
        updateControlState()
    }

    private fun onToggle(data: FieldData, index: Int) {
        if (index != checkedPosition) {
            checkedPosition = index
            val activity = data.scoutingActivity
            activity.vibrateAction()
            val entry = activity.entry
            if (entry != null) {
                entry.add(DataPoint(data.typeIndex, checkedPosition, activity.getRelativeTime()))
                updateControlState()
            }
        }
    }

    override fun updateControlState() {
        val fieldData = fieldData ?: return
        val entry = fieldData.scoutingActivity.entry ?: return

        val newPos = entry.lastValue(fieldData.typeIndex)?.value ?: defaultPosition
        if (newPos != checkedPosition) {
            checkedPosition = newPos
            toggleSwitch?.setCheckedPosition(checkedPosition)
        }
    }
}
