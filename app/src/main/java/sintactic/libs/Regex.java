package sintactic.libs;

public class Regex implements IRegex {
    public boolean esDIGITO(char c){ 
        String s = String.valueOf(c);
        return s.matches(DIGITO);
    }
    
    public boolean esOPERADOR(String c){ 
        return c.matches(OPERADOR);
    }
    
    public boolean esDELIMITADOR(char c){ 
       String s = String.valueOf(c);
       return s.matches(DELIMITADOR);
    }
    
    public boolean esMAYUSCULA(char c){ 
       String s = String.valueOf(c);
       return s.matches(MAYUSCULA);
    }
    
    public boolean esLETRA(char c){ 
        String s = String.valueOf(c);
        return s.matches(LETRA);
    }
    
    public boolean esRESERVADA(String c){
        return c.matches(RESERVADA);
    }
    
    public boolean esID(String c){
        return c.matches(ID);
    }
    
    public boolean esENTERO(String c){
        return c.matches(ENTERO);
    }
    
    public boolean esFLOTANTE(String c){
        return c.matches(FLOTANTE);
    }
}
