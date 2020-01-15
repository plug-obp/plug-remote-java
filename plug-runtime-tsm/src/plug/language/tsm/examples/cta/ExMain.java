package plug.language.tsm.examples.cta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import plug.language.tsm.ast.Behavior;
import plug.language.tsm.ast.BehaviorSoup;
import plug.language.tsm.ast.Channel;

public class ExMain {
	

	List<Behavior<Configuration>> waterTank() {
		List<Behavior<Configuration>> behaviors = new ArrayList<>();

        Behavior<Configuration> w2s =
                new Behavior<>(
                         "w2s",
                        (c) -> c.wtTriggerSensor,
                        (c) -> {
                        	c.wtTriggerSensor = false;
                        	return c;
                        }, 
                        Channel.out("measure")
                        ,true);

        Behavior<Configuration> r2i =
                new Behavior<>(
                         "r2i",
                        (c) -> c.wtWaterLevel < c.wtMaxWaterLevel,
                        (c) -> {
                        	c.wtWaterLevel+=1;
                        	c.wtTriggerSensor = true;
                        	return c;
                        }, 
                        Channel.in("increase")
                        ,false);

        Behavior<Configuration> i2f =
                new Behavior<>(
                         "i2f",
                        (c) -> c.wtWaterLevel == c.wtMaxWaterLevel,
                        (c) -> {
                        	c.wtOverflow = true;
                        	c.wtTriggerSensor = true;
                        	return c;
                        }, 
                        Channel.in("increase")
                        ,false);

        Behavior<Configuration> r2d =
                new Behavior<>(
                         "r2d",
                        (c) -> c.wtWaterLevel > 0,
                        (c) -> {
                        	c.wtWaterLevel-=1;
                        	c.wtTriggerSensor = true;
                        	return c;
                        }, 
                        Channel.in("decrease")
                        ,false);

        Behavior<Configuration> d2e =
                new Behavior<>(
                         "d2e",
                        (c) -> c.wtWaterLevel == 0,
                        (c) -> {
                        	c.wtTriggerSensor=true;
                        	return c;
                        }, 
                        Channel.in("decrease")
                        ,false);
        return Arrays.asList(w2s,r2i,i2f,r2d,d2e);
	}
	
	List<Behavior<Configuration>> plc() {
		List<Behavior<Configuration>> behaviors = new ArrayList<>();

        Behavior<Configuration> upd =
                new Behavior<>(
                         "upd",
                        (c) -> true,
                        (c) -> {
                        	c.plcWaterLevel=c.sWaterLevel;
                        	c.plcTriggerDecision=true;
                        	return c;
                        }, 
                        Channel.in("updatedLevel")
                        ,false);

        Behavior<Configuration> t2r =
                new Behavior<>(
                         "t2r",
                        (c) -> c.plcTriggerDecision
                        	&& c.plcWaterLevel<c.plcUpperThreshold
                        	&& c.plcWaterLevel>c.plcLowerThreshold,
                        (c) -> {
                        	c.plcTriggerDecision = false;
                        	c.plcTriggerRegular = true;
                        	return c;
                        }
                        ,true);

        Behavior<Configuration> t2u =
                new Behavior<>(
                         "t2u",
                        (c) -> c.plcTriggerDecision
                    		&& c.plcWaterLevel>=c.plcUpperThreshold,
                        (c) -> {
                        	c.plcTriggerDecision = false;
                        	c.plcTriggerDangerous = true;
                        	c.plcTriggerIV = true;
                        	c.plcTriggerPump = true;
                        	return c;
                        }
                        ,true);

        Behavior<Configuration> t2d =
                new Behavior<>(
                         "t2d",
                        (c) -> c.plcTriggerDecision
                			&& c.plcWaterLevel<=c.plcLowerThreshold,
                        (c) -> {
                        	c.plcTriggerDecision = false;
                        	c.plcTriggerDangerous = true;
                        	c.plcTriggerIV = true;
                        	c.plcTriggerPump = true;
                        	return c;
                        }
                        ,true);

        Behavior<Configuration> p2i =
                new Behavior<>(
                         "p2i",
                        (c) -> c.plcTriggerIV,
                        (c) -> {
                        	c.plcTriggerIV = false;
                        	return c;
                        }, 
                        Channel.out("commandIV")
                        ,true);

        Behavior<Configuration> p2p =
                new Behavior<>(
                         "p2p",
                        (c) -> c.plcTriggerPump,
                        (c) -> {
                        	c.plcTriggerPump = false;
                        	return c;
                        }, 
                        Channel.out("commandPump")
                        ,true);

        Behavior<Configuration> p2d =
                new Behavior<>(
                         "p2d",
                        (c) -> c.plcTriggerDangerous,
                        (c) -> {
                        	c.plcTriggerDangerous = false;
                        	return c;
                        }, 
                        Channel.out("dangerousLevel")
                        ,true);

        Behavior<Configuration> p2r =
                new Behavior<>(
                         "p2r",
                        (c) -> c.plcTriggerRegular,
                        (c) -> {
                        	c.plcTriggerRegular = false;
                        	return c;
                        }, 
                        Channel.out("regularLevel")
                        ,true);
        return Arrays.asList(upd,t2r,t2u,t2d,p2i,p2p);
	}
	
	List<Behavior<Configuration>> scada() {
		List<Behavior<Configuration>> behaviors = new ArrayList<>();

        Behavior<Configuration> e2r =
                new Behavior<>(
                         "e2r",
                        (c) -> c.sEmergency,
                        (c) -> {
                        	c.sEmergency=false;
                        	return c;
                        }, 
                        Channel.in("regularLevel")
                        ,false);

        Behavior<Configuration> r2r =
                new Behavior<>(
                        "r2r",
                       (c) -> !c.sEmergency,
                       (c) -> {
                       	return c;
                       }, 
                       Channel.in("regularLevel")
                       ,false);

        Behavior<Configuration> a2c =
                new Behavior<>(
                         "a2c",
                        (c) -> true,
                        (c) -> {
                        	c.sCorrupted = true;
                        	return c;
                        }, 
                        Channel.in("jamNetwork")
                        ,false);

        Behavior<Configuration> r2e =
                new Behavior<>(
                         "r2e",
                        (c) -> !c.sEmergency&&!c.sAlert&&!c.sCorrupted,
                        (c) -> {
                        	c.sEmergency=true;
                        	return c;
                        }, 
                        Channel.in("dangerousLevel")
                        ,false);

        Behavior<Configuration> e2a =
                new Behavior<>(
                         "e2a",
                        (c) -> c.sEmergency&&!c.sAlert&&!c.sCorrupted,
                        (c) -> {
                        	c.sAlert=true;
                        	return c;
                        }, 
                        Channel.in("dangerousLevel")
                        ,false);

        Behavior<Configuration> a2a =
                new Behavior<>(
                         "a2a",
                        (c) -> c.sAlert||c.sCorrupted,
                        (c) -> {
                        	return c;
                        }, 
                        Channel.in("dangerousLevel")
                        ,false);
        return Arrays.asList(e2r,r2r,r2e,e2a,a2c,a2a);
	}
	
	List<Behavior<Configuration>> inflowValve() {
		List<Behavior<Configuration>> behaviors = new ArrayList<>();

        Behavior<Configuration> o2o =
                new Behavior<>(
                         "o2o",
                        (c) -> c.iIsOpen,
                        (c) -> {
                        	return c;
                        }, 
                        Channel.out("increase")
                        ,false);
        
        Behavior<Configuration> f2f =
                new Behavior<>(
                         "f2f",
                        (c) -> c.iIsForced,
                        (c) -> {
                        	return c;
                        }, 
                        Channel.in("commandIV")
                        ,false);
        
        Behavior<Configuration> a2f =
                new Behavior<>(
                         "a2f",
                        (c) -> true,
                        (c) -> {
                        	c.iIsForced = true;
                        	c.iIsOpen = true;
                        	return c;
                        }, 
                        Channel.in("forceOpen")
                        ,false);
        
        Behavior<Configuration> a2a =
                new Behavior<>(
                         "o2o",
                        (c) -> !c.iIsForced,
                        (c) -> {
                        	c.iIsOpen=!c.iIsOpen;
                        	return c;
                        }, 
                        Channel.in("commandIV")
                        ,false);
        return Arrays.asList(o2o,f2f,a2f,a2a);
	}

	List<Behavior<Configuration>> attacker() {

        Behavior<Configuration> z2o =
                new Behavior<>(
                         "z2o",
                        (c) -> !c.aHasJammedNetwork,
                        (c) -> {
                        	c.aHasJammedNetwork = true;
                        	return c;
                        }, 
                        Channel.out("jamNetwork")
                        ,false);
        
        Behavior<Configuration> o2t =
                new Behavior<>(
                         "o2t",
                        (c) -> !c.aHasForced,
                        (c) -> {
                        	c.aHasForced = true;
                        	return c;
                        }, 
                        Channel.out("forceOpen")
                        ,false);
        
        Behavior<Configuration> t2t =
                new Behavior<>(
                         "t2t",
                        (c) -> !c.aHasManuallyInput,
                        (c) -> {
                        	c.aHasManuallyInput = true;
                        	return c;
                        }, 
                        Channel.out("manualInput")
                        ,false);
        
        return Arrays.asList(z2o,o2t,t2t);
	}
	
	List<Behavior<Configuration>> manualValve() {

        Behavior<Configuration> o2c =
                new Behavior<>(
                         "MV_o2c",
                        (c) -> c.mIsOpen,
                        (c) -> {
                        	c.mIsOpen = false;
                        	return c;	
                        }, 
                        Channel.in("manualInput")
                        ,false);

        Behavior<Configuration> c2c =
                new Behavior<>(
                         "MV_c2c",
                        (c) -> !c.mIsOpen,
                        (c) -> c, 
                        Channel.in("flow")
                        ,false);
        

        Behavior<Configuration> c2o =
                new Behavior<>(
                         "MV_c2o",
                        (c) -> !c.mIsOpen,
                        (c) -> {
                        	c.mIsOpen = true;
                        	return c;
                        }, 
                        Channel.in("manualInput")
                        ,false);        

        Behavior<Configuration> o2u =
                new Behavior<>(
                         "MV_o2u",
                        (c) -> c.mIsOpen,
                        (c) -> {
                        	c.mTriggerDecrease = true;
                        	return c;
                        }, 
                        Channel.in("flow")
                        ,false);

        Behavior<Configuration> u2o =
                new Behavior<>(
                         "MV_u2o",
                        (c) -> c.mTriggerDecrease,
                        (c) -> {
                        	c.mTriggerDecrease = false;
                        	return c;
                        }, 
                        Channel.out("decrease")
                        ,true);
        
        return Arrays.asList(o2c,c2c,c2o,o2u,u2o);
	}
	
	List<Behavior<Configuration>> pump() {

        Behavior<Configuration> o2o =
                new Behavior<>(
                         "o2o",
                        (c) -> c.pIsOpen,
                        (c) -> c, 
                        Channel.out("flow")
                        ,false);
        Behavior<Configuration> o2c =
                new Behavior<>(
                        "o2c",
                        (c) ->    c.pIsOpen,
                        (c) -> {
                            c.pIsOpen = false;
                            return c;
                        },
                        Channel.in("commandPump")
                        );
        Behavior<Configuration> c2o =
                new Behavior<>(
                         "c2o",
                        (c) -> !c.pIsOpen,
                        (c) -> {
                            c.pIsOpen = true;                            
                            return c;
						}, 
                        Channel.in("commandPump")							 
                        );
        return Arrays.asList(o2o, o2c, c2o);
	}
	
	List<Behavior<Configuration>> sensor() {

        Behavior<Configuration> f2c =
                new Behavior<>(
                         "f2c",
                        (c) -> true,
                        (c) -> {
                            c.sWaterLevel = c.wtWaterLevel;
                            c.sTriggerSensor = true;
                            return c;
						},  
                        Channel.in("measure")
                        ,false);
        Behavior<Configuration> c2f =
                new Behavior<>(
                         "c2f",
                         (c) -> c.sTriggerSensor,
                         (c) -> {
                             c.sTriggerSensor = false;                            
                             return c;
 						},   
                        Channel.out("updateLevel"),
                        true);
        return Arrays.asList(f2c,c2f);
	}
	
	 public BehaviorSoup<Configuration> model() {
         BehaviorSoup<Configuration> soup = new BehaviorSoup<>(new Configuration());

         List<Behavior<Configuration>> pump = pump();
         List<Behavior<Configuration>> sensor = sensor();
         List<Behavior<Configuration>> manualValve = manualValve();
         List<Behavior<Configuration>> attacker = attacker();
         List<Behavior<Configuration>> inflowValve = inflowValve();
         List<Behavior<Configuration>> scada = scada();
         List<Behavior<Configuration>> plc = plc();
         List<Behavior<Configuration>> waterTank = waterTank();

         soup.behaviors.addAll(pump);
         soup.behaviors.addAll(sensor);
         soup.behaviors.addAll(manualValve);
         soup.behaviors.addAll(attacker);
         soup.behaviors.addAll(inflowValve);
         soup.behaviors.addAll(scada);
         soup.behaviors.addAll(plc);
         soup.behaviors.addAll(waterTank);
         
         return soup;
     }
}
