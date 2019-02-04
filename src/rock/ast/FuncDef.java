package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockFunction;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.util.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FuncDef extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new FuncDef(elements);

    public FuncDef(ASTree... children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().literal();
    }

    public List<String> params() {
        ASTree ast = child(1);
        List<String> params = new ArrayList<>(ast.childCount());
        for (int i = 0; i < ast.childCount(); i++) {
            params.add(((ASTLeaf) ast.child(i)).token().literal());
        }
        return params;
    }

    public ASTree body() {
        return child(2);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        RockFunction func = new RockFunction(name(), params().toArray(new String[params().size()]), body(), env);
        Logger.log("put " + func);
        return env.set(func.name(), func);
    }

    @Override
    public String toString(int indent, String space) {
        StringBuffer sb = new StringBuffer();
        sb.append(RockString.repeat(indent, space)).append("def ").append(name()).append(" (");
        Iterator<String> itr = params().iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ").append(body().toString(indent, space));
        return sb.toString();
    }
}
