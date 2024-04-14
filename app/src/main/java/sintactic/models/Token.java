package sintactic.models;

public record Token(String lexema, String token, String descripcion, Object atributo, int linea) {
    @Override
    public String toString() {
        return String.format("%s %s %s %s %d", lexema, token, descripcion, atributo, linea);
    }
}