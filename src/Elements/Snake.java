package Elements;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import java.util.*;

/**
 * La clase Snake representa una serpiente en un juego que se basa en una cuadrícula,
 * modelada como una colección de segmentos conectados que forman el cuerpo de la serpiente.
 * Esta clase incluye métodos para manejar la dirección, el cuerpo y la representación gráfica.
 */
public final class Snake {
    // --- Constantes ---
    private static final float BODY_THICKNESS_RATIO = 0.8f;
    // --- Atributos ---
    private final PApplet parent;
    private final List<PVector> body;
    private final List<PVector> previousBody;
    private final Set<PVector> bodySet;
    private final PVector direction;
    private final int cellSize;
    private final int gridOffsetX;
    private final int gridOffsetY;
    private boolean isGrowing;
    private boolean isDead;

    /**
     * Construye un nuevo objeto Serpiente con los parámetros especificados.
     * Inicializa el cuerpo de la serpiente, la posición inicial y la dirección de movimiento por defecto.
     * @param parent la instancia PApplet que gestiona el contexto de renderizado.
     * @param startCol la posición inicial de la cabeza de la serpiente en la rejilla.
     * @param startRow la posición inicial de la cabeza de la serpiente en la cuadrícula.
     * @param cellSize el tamaño de cada celda de la cuadrícula, utilizado para calcular las posiciones.
     * @param gridOffsetX el desplazamiento horizontal de la cuadrícula en píxeles, que define la coordenada X inicial.
     * @param gridOffsetY el desplazamiento vertical de la rejilla en píxeles, definiendo la coordenada I inicial.
     */
    public Snake(final PApplet parent, final int startCol, final int startRow,
                 final int cellSize, final int gridOffsetX, final int gridOffsetY) {
        if(parent == null || cellSize <= 0 || gridOffsetX < 0 || gridOffsetY < 0){
            throw new IllegalArgumentException("Invalid parameters for Elements.Snake constructor");
        }
        this.parent = parent;
        this.cellSize = cellSize;
        this.gridOffsetX = gridOffsetX;
        this.gridOffsetY = gridOffsetY;
        // Inicializa el cuerpo de la serpiente con la posición inicial
        this.body = new LinkedList<>();
        this.previousBody = new LinkedList<>();
        this.bodySet = new HashSet<>();
        this.isDead = false;
        this.isGrowing = false;
        this.direction = new PVector(1, 0); // Derecha
        addSegment(new PVector(startCol - 2, startRow)); // Cola
        addSegment(new PVector(startCol - 1, startRow)); // Cuerpo
        addSegment(new PVector(startCol, startRow));     // Cabeza
        savePreviousBody();
    }

    /**
     * Añade un nuevo segmento al cuerpo de la serpiente y actualiza su conjunto interno de cuerpos.
     * Este método se utiliza para hacer crecer la serpiente o extender su cuerpo en una dirección dada.
     * @param segment el PVector que define la posición del nuevo segmento del cuerpo a añadir
     */
    private void addSegment(PVector segment) {
        this.body.addLast(segment);
        this.bodySet.add(segment);
    }

    /**
     * Elimina el segmento de cola del cuerpo de la serpiente y actualiza las estructuras de datos internas.
     * Este método asegura que la longitud del cuerpo de la serpiente se reduce eliminando el primer segmento
     * de la lista de cuerpos (`body`) y también eliminando su referencia del conjunto (`bodySet`)
     * para que las comprobaciones de colisión y pertenencia sean eficientes. Se utiliza para mantener el tamaño del cuerpo de la serpiente
     * durante las actualizaciones cuando la serpiente no está creciendo.
     */
    private void removeTailSegment() {
        if (!body.isEmpty()) {
            PVector tail = body.removeFirst(); // Quita del inicio de la lista O(1)
            this.bodySet.remove(tail);     // Quita del set O(1)
        }
    }

    /**
     * Guarda una copia del estado actual del cuerpo de la serpiente en la lista `previousBody`.
     * Este método borra el contenido existente de `previousBody` e itera a través de la lista actual de `body
     * actual `body` lista, añadiendo una copia de cada segmento para mantener una instantánea de la
     * estado del cuerpo de la serpiente. Cada segmento es copiado en profundidad para asegurar que su estado permanece
     * independiente de cualquier actualización posterior de la lista de «cuerpos».
     */
    private void savePreviousBody() {
        this.previousBody.clear();
        for (PVector segment : this.body) {
            this.previousBody.add(segment.copy());
        }
    }

    /**
     * Actualiza el estado de la serpiente recalculando la posición de su cabeza
     * y ajustando el cuerpo basado en su dirección de movimiento y estado de crecimiento.
     * La nueva posición de la cabeza se calcula en función de la dirección actual de la serpiente.
     * Si la serpiente está creciendo, la nueva posición se añade al
     * principio del cuerpo sin eliminar el último segmento. En caso contrario
     * En caso contrario, se elimina el último segmento del cuerpo para mantener el tamaño actual.
     */
    public void update(){
        savePreviousBody();
        PVector head = this.body.getLast();
        PVector newHead = head.copy().add(direction);
        addSegment(newHead);
        if(!isGrowing) {
            removeTailSegment();
        } else{
            isGrowing = false; // Resetear el estado de crecimiento
        }
    }

    public void draw(int snakeColor, float progress) {
        // Calcula dónde dibujar los centros en este frame
        PVector[] drawCenters = new PVector[body.size()];
        if(previousBody.size() == body.size()) { // Caso normal: interpolación entre dos estados
            for (int i = 0; i < body.size(); i++) {
                drawCenters[i] = interpolateCenter(body.get(i), previousBody.get(i), progress);
            }
        } else { // Caso especial: interpolación entre dos longitudes de cuerpo diferentes
            int headIndex = body.size() - 1;
            int oldHeadIndex = previousBody.size() - 1;
            drawCenters[headIndex] = interpolateCenter(body.get(headIndex), previousBody.get(oldHeadIndex), progress);
            // Interpola el resto del cuerpo (0 a N-1 en body) desde sus correspondientes (0 a N-1 en previous)
            for (int i = 0; i < headIndex; i++) {
                drawCenters[i] = interpolateCenter(body.get(i), previousBody.get(i), progress);
            }
        }
        // Dibujar la serpiente
        drawSnakeGeometry(drawCenters, snakeColor);
    }

    private PVector interpolateCenter(PVector current, PVector previous, float progress) {
        float prevCenterX = gridOffsetX + previous.x * cellSize + cellSize / 2.0f;
        float prevCenterY = gridOffsetY + previous.y * cellSize + cellSize / 2.0f;
        float currCenterX = gridOffsetX + current.x * cellSize + cellSize / 2.0f;
        float currCenterY = gridOffsetY + current.y * cellSize + cellSize / 2.0f;
        return new PVector(
                PApplet.lerp(prevCenterX, currCenterX, progress),
                PApplet.lerp(prevCenterY, currCenterY, progress)
        );
    }

    private void drawSnakeGeometry(PVector[] centers, int snakeColor) {
        float snakeThickness = cellSize * BODY_THICKNESS_RATIO;
        // Dibujar líneas conectadas
        parent.pushStyle();
        parent.strokeCap(PConstants.ROUND);
        parent.strokeJoin(PConstants.ROUND);
        parent.strokeWeight(snakeThickness);
        parent.stroke(snakeColor);
        for(int i = 0; i < centers.length - 1; i++) {
            PVector start = centers[i];
            PVector end = centers[i + 1];
            parent.line(start.x, start.y, end.x, end.y);
        }
        parent.popStyle();
        // 3. Dibuja Ojos Encima de la Cápsula de la Cabeza
        PVector headCenter = centers[centers.length - 1];
        drawEyes(headCenter.x, headCenter.y, snakeThickness);
    }

    /**
     * Dibuja los ojos y pupilas de la serpiente basándose en la posición de la cabeza proporcionada,
     * grosor, y la dirección actual de movimiento.
     * El método calcula las posiciones y tamaños de los ojos y pupilas
     * en relación con el centro y la dirección de la cabeza, y luego los dibuja.
     * @param headCenterX la coordenada X de la posición central de la cabeza.
     * @param headCenterY la coordenada Y de la posición central de la cabeza.
     * @param snakeThickness el grosor de la serpiente, utilizado para determinar
     * el tamaño y el espaciado de los ojos y las pupilas.
     */
    private void drawEyes(float headCenterX, float headCenterY, float snakeThickness) {
        float eyeSize = snakeThickness * 0.25f;
        float pupilSize = eyeSize * 0.5f;
        float eyeDistanceFactor = 0.25f;
        float eyeOffsetX = snakeThickness * eyeDistanceFactor;
        float eyeOffsetY = snakeThickness * eyeDistanceFactor;
        float pupilOffset = eyeSize * 0.15f;

        float eye1X = 0, eye1Y = 0, eye2X = 0, eye2Y = 0;
        float pupil1X = 0, pupil1Y = 0, pupil2X = 0, pupil2Y = 0;

        if (direction.x == 1) { eye1X = headCenterX; eye1Y = headCenterY - eyeOffsetY; eye2X = headCenterX; eye2Y = headCenterY + eyeOffsetY; pupil1X = eye1X + pupilOffset; pupil1Y = eye1Y; pupil2X = eye2X + pupilOffset; pupil2Y = eye2Y; }
        else if (direction.x == -1) { eye1X = headCenterX; eye1Y = headCenterY - eyeOffsetY; eye2X = headCenterX; eye2Y = headCenterY + eyeOffsetY; pupil1X = eye1X - pupilOffset; pupil1Y = eye1Y; pupil2X = eye2X - pupilOffset; pupil2Y = eye2Y; }
        else if (direction.y == -1) { eye1X = headCenterX - eyeOffsetX; eye1Y = headCenterY; eye2X = headCenterX + eyeOffsetX; eye2Y = headCenterY; pupil1X = eye1X; pupil1Y = eye1Y - pupilOffset; pupil2X = eye2X; pupil2Y = eye2Y - pupilOffset; }
        else if (direction.y == 1) { eye1X = headCenterX - eyeOffsetX; eye1Y = headCenterY; eye2X = headCenterX + eyeOffsetX; eye2Y = headCenterY; pupil1X = eye1X; pupil1Y = eye1Y + pupilOffset; pupil2X = eye2X; pupil2Y = eye2Y + pupilOffset; }
        else { eye1X = headCenterX - eyeOffsetX; eye1Y = headCenterY - eyeOffsetY; eye2X = headCenterX + eyeOffsetX; eye2Y = headCenterY + eyeOffsetY; pupil1X = eye1X; pupil1Y = eye1Y; pupil2X = eye2X; pupil2Y = eye2Y; }

        parent.fill(255); parent.ellipse(eye1X, eye1Y, eyeSize, eyeSize); parent.ellipse(eye2X, eye2Y, eyeSize, eyeSize);
        parent.fill(0); parent.ellipse(pupil1X, pupil1Y, pupilSize, pupilSize); parent.ellipse(pupil2X, pupil2Y, pupilSize, pupilSize);
    }

    /**
     * Comprueba si una celda específica de la cuadrícula colisiona con alguna parte del cuerpo de la serpiente.
     * @param checkCol el índice de la columna de la celda de la rejilla para comprobar la colisión.
     * @param checkRow el índice de fila de la celda de la rejilla para comprobar la colisión.
     * @return true si la celda de la rejilla especificada colisiona con alguna parte del cuerpo de la serpiente; false en caso contrario.
     */
    public boolean checkCollisionWithBody(int checkCol, int checkRow) {
        PVector cellToCheck = new PVector(checkCol, checkRow);
        return bodySet.contains(cellToCheck);
    }

    public boolean checkSelfCollision() {
        PVector head = this.body.getLast();
        // Requiere al menos 4 segmentos para poder chocar de forma realista
        if (body.size() < 4) return false;
        int headIndex = body.size() - 1;
        for (PVector segment : this.body) {
            if(segment == head) continue;
            if (head.x == segment.x && head.y == segment.y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Establece la nueva dirección de la serpiente.
     * Evita que la serpiente se mueva en la dirección exactamente opuesta.
     * @param direction Cambio en columnas y filas (-1 izquierda/arriba, 1 derecha/abajo, 0 sin cambio).
     */
    public void setDirection(PVector direction) {
        // Cambiar la dirección de la serpiente
        if (direction.x == -this.direction.x && direction.y == -this.direction.y) {
            return;
        }
        this.direction.set(direction);
    }

    /**
     * Recupera la posición de la cola de la serpiente como un vector.
     * @return la posición de la cola de la serpiente como un objeto PVector.
     */
    public PVector getDirection(){
        return this.direction.copy();
    }

    /**
     * Comprueba si la serpiente ha muerto.
     * @return true si la serpiente ha muerto; false en caso contrario.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Recupera el cuerpo actual de la serpiente como una lista de vectores de posición.
     * @return a Lista de objetos PVector que representan los segmentos del cuerpo de la serpiente.
     */
    public List<PVector> getBody() {
        return body;
    }

    /**
     * Activa el modo de crecimiento para la serpiente, permitiéndole extender su cuerpo
     * la próxima vez que se llame al método update.
     * Cuando la serpiente está en el estado de crecimiento, un segmento adicional del cuerpo
     * será retenido durante las actualizaciones en lugar
     * de ser eliminado, aumentando efectivamente su longitud.
     */
    public void grow() { isGrowing = true; }
}
