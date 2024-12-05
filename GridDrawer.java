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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println(x + ", " + y);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        int cellWidth = width / gridSize;
        int cellHeight = height / gridSize;

        for (int i = 0; i <= gridSize; i++) {
            // Horizontal lines
            g.drawLine(0, i * cellHeight, width, i * cellHeight);
            // Vertical lines
            g.drawLine(i * cellWidth, 0, i * cellWidth, height);
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
        filledCells.add(new Point(col, row)); // Add the cell to the filled set
        // repaint(); // Trigger a repaint to show the update
    }

    // Remove filled cell from HashSet
    public void removeCell(int row, int col) {
        Point cell = new Point(col, row);
        if (filledCells.contains(cell)) {
            filledCells.remove(cell);
            // repaint();
        }
    }

    /*public static void main(String[] args) {
        int gridSize = Integer.parseInt(JOptionPane.showInputDialog("Enter the size of the grid (N):"));
        
        JFrame frame = new JFrame("Grid Drawer");
        GridDrawer gridDrawer = new GridDrawer(gridSize);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // Set a default size
        frame.add(gridDrawer);
        frame.setVisible(true);

        // Example: Fill cell [1][1] and [2][3] after a delay
        gridDrawer.fillCell(1, 1); // Fill cell [1][1]
    }*/
}

