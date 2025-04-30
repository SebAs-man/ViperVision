package Elements;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * La clase Apple representa una manzana que se utiliza como elemento del juego, posicionada
 * dentro de una cuadrícula. Este objeto puede generarse en lugares aleatorios asegurando
 * que no colisione con otros elementos como el cuerpo de la serpiente, y puede ser visualizado
 * mediante un gráfico o una representación alternativa.
 * Esta clase es inmutable, excepto por las posiciones de la fila y columna que definen
 * la ubicación de la manzana en la cuadrícula del juego.
 */
public final class Apple {
    // --- Atributos ---
    private final PApplet parent; // referencia a la clase principal
    private int row;
    private int col;
    private final int cellSize;
    private final int gridOffsetX;
    private final int gridOffsetY;

    /**
     * Construye un objeto Apple con el padre, tamaño de celda y desplazamientos de rejilla especificados.
     * @param parent la referencia a la instancia PApplet que gestiona el renderizado.
     * @param cellSize el tamaño de cada celda en la rejilla del juego, utilizado para calcular las posiciones.
     * @param gridOffsetX el desplazamiento horizontal de la rejilla en píxeles, que define el punto de inicio de la rejilla.
     * @param gridOffsetY el desplazamiento vertical de la rejilla en píxeles, definiendo el punto de inicio de la rejilla.
     */
    public Apple(final PApplet parent, final int cellSize, final int gridOffsetX, final int gridOffsetY) {
        this.parent = parent;
        this.cellSize = cellSize;
        this.gridOffsetX = gridOffsetX;
        this.gridOffsetY = gridOffsetY;
    }

    /**
     * Despliega la manzana en una posición aleatoria dentro de los límites de la cuadrícula del juego, asegurándose de
     * que no se superponga con el cuerpo de la serpiente.
     * @param snake el objeto Snake usado para comprobar la colisión con su cuerpo.
     * Si es nulo, se omite la comprobación de colisión.
     * @param maxRows el número máximo de filas en la rejilla del juego, utilizado para determinar
     * el rango válido para la posición de la fila de la manzana.
     * @param maxCols el número máximo de columnas en la cuadrícula del juego, utilizado para determinar
     * el rango válido para la posición de la columna de la manzana.
     */
    public void spawn(Snake snake, int maxRows, int maxCols) {
        boolean collision;
        int attempts = 0;
        int maxAttempts = maxRows * maxCols * 2; // Número máximo de intentos para evitar un bucle infinito.
        do{
            // Genera una nueva posición aleatoria para la manzana dentro de los límites de la cuadrícula.
            this.row = (int) parent.random(maxRows);
            this.col = (int) parent.random(maxCols);
            collision = (snake != null) && snake.checkCollisionWithBody(this.col, this.row);
            attempts++;
        } while(collision && attempts < maxAttempts);
    }

    /**
     * Dibuja la representación de la manzana en la cuadrícula del juego. Si se proporciona una imagen,
     * se dibuja centrada en la celda apropiada de la rejilla con escala aplicada. Si no hay imagen
     * se dibuja un círculo rojo como representación alternativa.
     * @param appleImage el objeto PImage que representa la apariencia gráfica de la manzana.
     * Si es nulo, se dibuja un círculo rojo en su lugar.
     */
    public void draw(PImage appleImage) {
        if(appleImage == null) { // Dibujar un círculo rojo.
            float fallbackX = this.gridOffsetX + this.col * this.cellSize + (this.cellSize / 2.0f);
            float fallbackY = this.gridOffsetY + this.row * this.cellSize + (this.cellSize / 2.0f);
            parent.fill(255, 0, 0);
            parent.ellipseMode(PConstants.CENTER);
            parent.ellipse(fallbackX, fallbackY, this.cellSize * 0.8f, this.cellSize * 0.8f);
            return;
        }
        // Lógica para centrar la manzana
        float cellCenterX = this.gridOffsetX + this.col * this.cellSize + (this.cellSize / 2.0f);
        float cellCenterY = this.gridOffsetY + this.row * this.cellSize + (this.cellSize / 2.0f);
        float drawX = cellCenterX - (cellSize / 2.0f);
        float drawY = cellCenterY - (cellSize / 2.0f);
        parent.imageMode(PConstants.CORNER);
        parent.image(appleImage, drawX, drawY, cellSize, cellSize);
        parent.imageMode(PConstants.CENTER);
    }

    /**
     * Obtiene el valor de fila (row) correspondiente a la posición actual
     * del objeto Apple en la cuadrícula del área de juego.
     * @return el índice de fila del objeto Elements.Apple en la cuadrícula
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtiene el valor de columna (col) correspondiente a la posición actual
     * del objeto Apple en la cuadrícula del área de juego.
     * @return el índice de columna del objeto Apple en la cuadrícula
     */
    public int getCol() {
        return col;
    }
}
