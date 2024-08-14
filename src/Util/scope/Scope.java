package Util.scope;

import AST.Type.Type;

import java.util.HashMap;

public class Scope {
    public HashMap<String, Type> vars;
    private Scope parent;

    public Scope(Scope parent) {
        this.parent = parent;
    }

    public void addVar(String name, Type type) {
        vars.put(name, type);
    }
}
