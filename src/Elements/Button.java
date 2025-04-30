package Elements;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.function.Function;

public final class Button {
    // --- Constantes ---
    public static final int WIDTH = 200;
    public static final int HEIGHT = 50;
    private static final int PADDING = 10;
    private static final int RADIUS = 10;
    // --- Atributos ---
    private final int x;
    private final int y;
    private final PImage icon;
    private final String text;
    private final int color;
    private final ButtonAction onClick;
    private final PApplet parent;

    /**
     * Construye un nuevo Botón con el icono, texto, posición, color especificados,
     * e instancia PApplet padre.
     * @param icon la PImagen que representa el icono mostrado en el botón
     * @param text el texto de la etiqueta mostrada en el botón
     * @param color el color del botón
     * @param parent la instancia de PApplet que sirve de padre para el renderizado
     */
    public Button(PImage icon, String text, int color, int x, int y, ButtonAction onClick, PApplet parent) {
        if(parent == null || x < 0 || y < 0) throw new IllegalArgumentException("Invalid parameters for Button constructor");
        this.icon = icon;
        this.text = text;
        this.color = color;
        this.x = x;
        this.y = y;
        this.onClick = onClick;
        this.parent = parent;
    }

    /**
     * Renderiza el botón con su apariencia definida, incluyendo su forma rectangular,
     * icono (si está presente), y etiqueta de texto. Este método utiliza la librería Processing
     * para dibujar elementos gráficos.
     * La apariencia del botón está determinada por sus coordenadas, tamaño, color y etiqueta de texto.
     * etiqueta de texto, con la inclusión opcional de un icono. El texto y el icono se alinean dinámicamente
     * en función de la presencia del icono.
     * El método también gestiona la configuración del contexto gráfico para el dibujo del botón
     * y los restablece a sus estados por defecto después de la ejecución para garantizar que ningún cambio no intencionado
     * cambios involuntarios afecten a otros dibujos.
     */
    public void draw() {
        // Configuración de la apariencia del botón
        parent.rectMode(PConstants.CENTER);
        parent.imageMode(PConstants.CORNER);
        parent.textAlign(PConstants.CENTER, PConstants.CENTER);
        // Dibuja el botón
        float btnLeftX = x - (WIDTH / 2.f);
        parent.fill(color);
        parent.rect(x, y, WIDTH, HEIGHT, RADIUS);

        float iconDrawSize = PApplet.min(icon != null ? icon.height : HEIGHT, HEIGHT - (2 * PADDING));
        float iconX = btnLeftX + PADDING;
        float iconY = y - iconDrawSize / 2.0f;
        float textAreaX_Start;
        if(icon != null) {
            parent.image(icon, iconX, iconY, iconDrawSize, iconDrawSize);
            textAreaX_Start = iconX + iconDrawSize + PADDING;
        }
        else {
            textAreaX_Start = btnLeftX + PADDING;
        }
        parent.fill(254, 254, 255);
        parent.textSize(24);
        float textAreaX_End = x + (WIDTH / 2.0f) - PADDING;
        float textAreaWidth = textAreaX_End - textAreaX_Start;
        float textAreaCenterX = textAreaX_Start + textAreaWidth / 2.0f;
        parent.text(text, textAreaCenterX, y);
        // Reiniciar la configuración de la apariencia
        parent.rectMode(PConstants.CORNER);
        parent.imageMode(PConstants.CENTER);
    }

    /**
     * Determina si el cursor del ratón está situado sobre el botón.
     * @return true si el cursor del ratón se encuentra dentro de los límites del botón; false en caso contrario.
     */
    public boolean isMouseOver(float mouseX, float mouseY) {
        float btnLeftX = x - (WIDTH / 2.f);
        float btnRightX = x + (WIDTH / 2.f);
        float btnTopY = y - (HEIGHT / 2.f);
        float btnBottomY = y + (HEIGHT / 2.f);
        // Verifica si el mouse está dentro de los límites del botón
        return mouseX >= btnLeftX && mouseX <= btnRightX &&
                mouseY >= btnTopY && mouseY <= btnBottomY;
    }

    /**
     * Ejecuta la acción asociada al botón si se ha establecido. Si no se ha definido ninguna acción
     * está definida, se imprime un mensaje por defecto para indicar la ausencia de una acción.
     * El método comprueba si la acción `onClick` no es nula. Si no es nula
     * invoca el método `execute` del objeto `onClick`. Si es nulo, muestra
     * un mensaje a la consola indicando que la acción del botón no está definida.
     */
    public void performAction() {
        if(onClick != null) {
            onClick.execute();
        }else{
            System.out.println("Button action is null");
        }
    }
}
