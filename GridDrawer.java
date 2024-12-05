import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class GridDrawer extends JPanel {
    private int gridSize;
    private Set<Point> filledCells; // To track filled cells

    public GridDrawer(int gridSize) {
        this.gridSize = gridSize;
        this.filledCells = new HashSet<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        int cellWidth = width / gridSize;
        int cellHeight = height / gridSize;

        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(0, i * cellHeight, width, i * cellHeight); // horizontal lines
            g.drawLine(i * cellWidth, 0, i * cellWidth, height); // vertical lines
        }

        g.setColor(Color.BLUE); 
        for (Point cell : filledCells) {
            int x = cell.x * cellWidth;
            int y = cell.y * cellHeight;
            g.fillRect(x, y, cellWidth, cellHeight);
        }
    }

    // Method to fill a specific cell
    public void fillCell(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Cell indices out of bounds.");
        }
        Point cell = new Point(col, row);
        if (!filledCells.contains(cell))
            filledCells.add(cell); // Add the cell to the filled set
    }

    // Remove filled cell from HashSet
    public void removeCell(int row, int col) {
        Point cell = new Point(col, row);
        if (filledCells.contains(cell)) {
            filledCells.remove(cell);
        }
    }

    public void clearGrid() {
        filledCells.clear();
    }
}

