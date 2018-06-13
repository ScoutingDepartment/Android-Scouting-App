package ca.warp7.android.scouting;

/**
 * Stores and integer-encodes a single datum in a match scouting session
 * <p>
 * As of now, a datum is 16 bits long and has the following properties
 * -- Undo flag at bit 1
 * -- State flag at bit 2
 * -- Data Type at bits 3-8
 * -- Data Value at bits 9-16
 * (from left to right)
 *
 * @author Team 865
 */


class EntryDatum {

    private int mDataType;
    private int mDataValue;
    private int mUndoFlag;
    private int mStateFlag;

    // This data is not encoded; it is used to determine the order of data points
    private int mRecordedTime;


    private EntryDatum(int type, int value) {
        mDataType = type;
        mDataValue = value;
    }

    EntryDatum(int type, int value, int recordedTime) {
        this(type, value);
        mRecordedTime = recordedTime;
    }

    int getType() {
        return mDataType;
    }

    int getValue() {
        return mDataValue;
    }

    int getUndoFlag() {
        return mUndoFlag;
    }

    int getStateFlag() {
        return mStateFlag;
    }

    void setValue(int value) {
        this.mDataValue = value;
    }

    public int getRecordedTime() {
        return mRecordedTime;
    }

    void flagAsUndone() {
        this.mUndoFlag = 1;
    }

    void setStateFlag(int stateFlag) {
        this.mStateFlag = stateFlag;
    }
}
