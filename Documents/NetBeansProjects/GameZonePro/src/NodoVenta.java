class NodoVenta {
    Venta venta;
    NodoVenta siguiente;

    public NodoVenta(Venta venta) {
        this.venta = venta;
        this.siguiente = null;
    }
}