public class Usuario {

    private String nombre;
    private int xp;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.xp = 0;
    }

    public void agregarXP(int puntos) {
        xp += puntos;
    }

    public int getXP() {
        return xp;
    }

    public String getNombre() {
        return nombre;
    }

        public int getNivel() {
        int xp = this.xp;

        if (xp < 500) return 1;
        else if (xp < 1500) return 2;
        else if (xp < 3500) return 3;
        else if (xp < 7000) return 4;
        else return 5;
        }

        public String getRango() {
            switch (getNivel()) {
                case 1: return "Aprendiz";
                case 2: return "Jugador";
                case 3: return "Veterano";
                case 4: return "Maestro";
                case 5: return "Leyenda";
                default: return "";
            }
        }
        
        public int getXPProgreso() {
            int xp = this.xp;

            if (xp < 500) return xp;
            else if (xp < 1500) return xp - 500;
            else if (xp < 3500) return xp - 1500;
            else if (xp < 7000) return xp - 3500;
            else return xp - 7000;
        }

        public int getXPNecesario() {
            int xp = this.xp;

            if (xp < 500) return 500;
            else if (xp < 1500) return 1000;
            else if (xp < 3500) return 2000;
            else if (xp < 7000) return 3500;
            else return 1; // nivel max
        }
        
        
}