package Language;

// creates a transfer function
// --StartStateID: the id of the state that the pointer is moving from
// --TransmissionValue: the char that invokes the movement
// --EndStateID: the id of the state that the pointer is moving to
public class TransferFunction {
    private int StartStateID;
    private Character TransmissionValue;
    private int EndStateID;

    public TransferFunction(int StartStateID, Character TransmissionValue, int EndStateID){
        this.StartStateID=StartStateID;
        this.TransmissionValue=TransmissionValue;
        this.EndStateID=EndStateID;
    }

    public void setEndStateID(int endStateID) {
        EndStateID = endStateID;
    }

    public Character getTransmissionValue() {
        return TransmissionValue;
    }

    public int getEndStateID() {
        return EndStateID;
    }

    public int getStartStateID() {
        return StartStateID;
    }

    public void setStartStateID(int startStateID) {
        StartStateID = startStateID;
    }

    public void setTransmissionValue(Character transmissionValue) {
        TransmissionValue = transmissionValue;
    }
}
