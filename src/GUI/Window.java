package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame{
    private static int numOfNodes = 0;

    private static final long serialVersionUID = 1L;
    int px, py;

    //----------------------------------------------------------
    //           ALL GUI COMPONENTS DECLERATIONS
    //----------------------------------------------------------
    /* COLORS: */
    private static final Color color_header = new Color(20,20,20);
    private static final Color color_hoverHeader = new Color(42, 196, 176, 255);
    private static final Color color_menu = new Color(40,40,40);
    private static final Color color_text_menu = new Color(255,255,255);
    /* Top bar: */
    private final JPanel Header = new JPanel();
    private final JPanel closePNL = new JPanel();
    private final JLabel closeWindowBTN = new JLabel();
    private final JPanel MaxClosePanel = new JPanel();
    private final JLabel maxWindowBTN = new JLabel();
    private final JPanel maximizePNL = new JPanel();
    /* side manu: */
    private final JPanel sideBar = new JPanel();
    private final JLabel btn_toggleMenu = new JLabel();
    private final JLabel btn_page1 = new JLabel();
    private final JLabel btn_page2 = new JLabel();
    private final JLabel btn_page3 = new JLabel();
    private static boolean isMenuOpen = false;
    /* pages panels: */
    private final JPanel pages = new JPanel(new CardLayout());
    private final Hungarian_FramePanel framePanel1 = new Hungarian_FramePanel();
    private final Edmonds_FramePanel framePanel2 = new Edmonds_FramePanel();
    private final JPanel framePanel3 = new JPanel();

    //----------------------------------------------------------
    //                          INIT
    //----------------------------------------------------------
    public Window() {
        super("Graph Algorithms");
        this.initComponents();
        setWindowResizable(true);
        // this.add(page1, BorderLayout.CENTER);
        this.pack();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(900,500));
        this.setLocationByPlatform(true);
        this.setUndecorated(true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/data/pokeball.png")));
        this.setLayout(new BorderLayout());
        this.addOtherComponents();
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    private void addOtherComponents() {

        //----------------------------------------------------------
        //                      HEADER BAR
        //----------------------------------------------------------
        Header.setBackground(color_header);
        Header.setMinimumSize(new Dimension(150, 20));

        //
        Header.setPreferredSize(new Dimension(800, 30));
        Header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                px = e.getX();
                py = e.getY();
            }
        });
        Header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                setLocation(e.getXOnScreen()-px, e.getYOnScreen()-py);
            }
        });
        Header.setLayout(new BorderLayout());

        JLabel title = new JLabel("  Graphs Algorithms");
        title.setFont(new Font("Arial", Font.ITALIC, 15));
        title.setForeground(Color.white);

        closePNL.setBackground(color_header);
        closePNL.setLayout(new BorderLayout());

        closeWindowBTN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeWindowBTN.setIcon(new ImageIcon(getClass().getResource("/data/icons8_close_window_18px.png"))); // NOI18N
        closeWindowBTN.setAlignmentY(0.0F);
        closeWindowBTN.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        closeWindowBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        closeWindowBTN.setMaximumSize(new Dimension(30, 30));
        closeWindowBTN.setPreferredSize(new Dimension(30, 30));
        closeWindowBTN.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                System.exit(0);
            }
            public void mouseEntered(MouseEvent evt) {
                closePNL.setBackground(color_hoverHeader);
            }
            public void mouseExited(MouseEvent evt) {
                closePNL.setBackground(color_header);
            }
        });
        closePNL.add(closeWindowBTN, BorderLayout.PAGE_START);


        maximizePNL.setBackground(color_header);
        maximizePNL.setLayout(new BorderLayout());
        maxWindowBTN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maxWindowBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/data/icons8_full_screen_18px.png"))); // NOI18N
        maxWindowBTN.setPreferredSize(new Dimension(30, 30));
        maxWindowBTN.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                maxWindowBTNMouseClicked();
            }
            public void mouseEntered(MouseEvent evt) {
                maximizePNL.setBackground(color_hoverHeader);
            }
            public void mouseExited(MouseEvent evt) {
                maximizePNL.setBackground(color_header);
            }
        });
        maximizePNL.add(maxWindowBTN, BorderLayout.PAGE_START);

        MaxClosePanel.setLayout(new GridBagLayout());
        MaxClosePanel.add(maximizePNL);
        MaxClosePanel.add(closePNL);

        Header.add(title, BorderLayout.WEST);
        Header.add(MaxClosePanel, BorderLayout.LINE_END);
        getContentPane().add(Header, BorderLayout.PAGE_START);

        //----------------------------------------------------------
        //                      SIDE MENU
        //----------------------------------------------------------
        sideBar.setPreferredSize(new Dimension(50, getHeight()));
        sideBar.setBackground(color_menu);
        btn_toggleMenu.setText("Open/Close Menu bar");
        btn_page1.setText("Shortest Path");
        btn_page2.setText("hungarian algorithm");
        btn_page3.setText("Edmonds blossom algorithm");

        btn_toggleMenu.setForeground(color_text_menu);
        btn_page1.setForeground(color_text_menu);
        btn_page2.setForeground(color_text_menu);
        btn_page3.setForeground(color_text_menu);

        btn_toggleMenu.setIcon(new ImageIcon(getClass().getResource("/data/rightArrow.png")));
        btn_page1.setIcon(new ImageIcon(getClass().getResource("/data/hungarianIcon.png")));
        btn_page2.setIcon(new ImageIcon(getClass().getResource("/data/EdmondsIcon.png")));
        btn_page3.setIcon(new ImageIcon(getClass().getResource("/data/task3.png")));

        btn_toggleMenu.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                toggleMenuClicked();
            }
            public void mouseEntered(MouseEvent evt) {
                btn_toggleMenu.setBackground(color_hoverHeader);
            }
            public void mouseExited(MouseEvent evt) {
                btn_toggleMenu.setBackground(color_header);
            }
        });
        btn_page1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                switchPage("hungarian");
            }
            public void mouseEntered(MouseEvent evt) {
                btn_page1.setBackground(color_hoverHeader);
            }
            public void mouseExited(MouseEvent evt) {
                btn_page1.setBackground(color_header);
            }
        });
        btn_page2.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                switchPage("edmonds");
            }
            public void mouseEntered(MouseEvent evt) {
                btn_page2.setBackground(color_hoverHeader);
            }
            public void mouseExited(MouseEvent evt) {
                btn_page2.setBackground(color_header);
            }
        });
        btn_page3.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                switchPage("line cover");
            }
            public void mouseEntered(MouseEvent evt) {
                btn_page3.setBackground(color_hoverHeader);
            }
            public void mouseExited(MouseEvent evt) {
                btn_page3.setBackground(color_header);
            }
        });


        sideBar.add(btn_toggleMenu);
        sideBar.add(btn_page1);
        sideBar.add(btn_page2);
        sideBar.add(btn_page3);

        sideBar.setLayout(new GridLayout(10,1));
        sideBar.setSize(30, 50);
        getContentPane().add(sideBar, BorderLayout.WEST);

        //----------------------------------------------------------
        //                      PAGES
        //----------------------------------------------------------
        framePanel1.setName("hungarian");
        framePanel2.setName("edmonds");
        framePanel3.setName("line cover");


        pages.add(framePanel1, "hungarian");
        pages.add(framePanel2, "edmonds");
        pages.add(framePanel3, "line cover");
        getContentPane().add(pages, BorderLayout.CENTER);
    }

    protected void toggleMenuClicked() {
        if(isMenuOpen){
            sideBar.setPreferredSize(new Dimension(50 ,getHeight()));
            btn_toggleMenu.setIcon(new ImageIcon(getClass().getResource("/data/rightArrow.png")));
        }
        else{
            sideBar.setPreferredSize(new Dimension(250 ,getHeight()));
            btn_toggleMenu.setIcon(new ImageIcon(getClass().getResource("/data/leftArrow.png")));
        }
        isMenuOpen = !isMenuOpen;
        SwingUtilities.updateComponentTreeUI(this);
    }

    protected void switchPage(String pageName) {
        CardLayout cl = (CardLayout)(pages.getLayout());
        cl.show(pages, pageName);
    }


    private void maxWindowBTNMouseClicked() {
        if(this.getExtendedState() != Window.MAXIMIZED_BOTH){
            this.setExtendedState(Window.MAXIMIZED_BOTH);
        }
        else{
            this.setExtendedState(Window.NORMAL);
        }
    }

    public void setWindowResizable(boolean b){
        if(!b) return;
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        ComponentResizer cr = new ComponentResizer();
        cr.registerComponent(this);
    }
}
