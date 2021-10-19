package plug.language.tsm.examples.cta;

//import plug.language.tsm.examples.mutex.peterson.Actor;
//import plug.language.tsm.examples.mutex.peterson.State;

public class Configuration {
    //public State state[] = new State[]{State.IDLE, State.IDLE};
    //public Actor turn = Actor.Alice;
    
	//WaterTank
	public int wtWaterLevel = 5;
	public int wtMaxWaterLevel = 10;
	public boolean wtOverflow = false;
	public boolean wtTriggerSensor = false;
	
	//PLC
	public boolean plcTriggerDecision = false;
	public boolean plcTriggerIV = false;
	public boolean plcTriggerPump = false;
	public boolean plcTriggerRegular = false;
	public boolean plcTriggerDangerous = false;
	public int plcWaterLevel = 42;
	public int plcUpperThreshold = 9;
	public int plcLowerThreshold = 1;
	
	//SCADA
	public boolean sAlert = false;
	public boolean sEmergency = false;
	public boolean sCorrupted = false;
	
	//InflowValve
	public boolean iIsOpen = true;
	public boolean iIsForced = false;
	
	//Attacker
	public boolean aHasForced = false;
	public boolean aHasManuallyInput = false;
	public boolean aHasJammedNetwork = false;
	
	//ManualValve
	public boolean mIsOpen = true;
	public boolean mTriggerDecrease = false;
	
	//Pump
	public boolean pIsOpen = false;
    
	//Sensor
    public boolean sTriggerSensor = false;
    public int sWaterLevel = 10;
}
