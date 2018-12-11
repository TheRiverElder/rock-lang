package rock.data.internal;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.exception.RockException;

public class RockObject extends RockAdapter {

    public static final String INVOKE = "__invoke__";
    public static final String GET = "__get__";
    public static final String SET = "__set__";
    public static final String COMPUTE = "__compute__";

    private RockClass clazz;
    private NestedEnvironment env;

    public RockObject(RockClass clazz, Environment outer) {
        this.clazz = clazz;
        env = new NestedEnvironment(outer);
    }

    @Override
    public Rock get(Object key) throws RockException {
        /*Rock func = env.get(GET);
        if (func != null) {
            return func.invoke(new RockString(String.valueOf(key)));
        }*/
        return env.get(key);
    }

    @Override
    public Rock set(Object key, Rock val) throws RockException {
        /*Rock func = env.get(SET);
        if (func != null) {
            return func.invoke(new RockString(String.valueOf(key)), val);
        }*/
        return env.set(key, val);
    }

    @Override
    public Rock compute(String op, Rock another) throws RockException {
        Rock func = env.get(COMPUTE);
        if (func != null) {
            return func.invoke(new RockString(op), another);
        }
        return super.compute(op, another);
    }

    @Override
    public Rock invoke(Rock... args) throws RockException {
        Rock func = env.get(INVOKE);
        if (func != null) {
            return func.invoke(args);
        }
        return super.invoke(args);
    }

    @Override
    public NestedEnvironment env() throws RockException {
        return env;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<object:").append(" instanceof ").append(clazz.name());
        return sb.append(">").toString();
    }
}