package ui;

import model.Entry;
import model.MyJournal;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents the GUI for the Journal App
public class JournalAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/journal.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private MyJournal myJournal;

    private Timer timer;
    private JProgressBar progress;
    private JLabel titleLbl;
    private JPanel menuPanel;
    private JPanel saveLoadPanel;
    private JButton saveBtn;
    private JButton loadBtn;
    private JPanel entryPanel;
    private JButton addBtn;
    private JWindow addEntryFormWindow;
    private JWindow seeMoreWindow;
    private JTextField titleInput;
    private JTextField dateInput;
    private JTextArea contentInput;
    private JButton cancelBtn;
    private JButton confirmBtn;
    private JButton backBtn;

    private static final int FRAME_WIDTH = 1100;
    private static final int FRAME_HEIGHT = 690;
    private static final int FORM_WIDTH = 300;
    private static final int FORM_HEIGHT = 300;
    private static final int SEE_MORE_HEIGHT = 300;
    private static final int SEE_MORE_WIDTH = 300;
    private static final int BTN_WIDTH = 120;
    private static final int BTN_HEIGHT = 40;
    private static final int PADDING = 40;

    private static final Color DARK_BLUE = new Color(32, 88, 144);
    private static final Color WHITE = new Color(255, 255, 255);

    // Creates the GUI for the JournalApp
    public JournalAppGUI() {
        super("My Check-in Journal");
        renderLoadingScreen();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 0, 20));

        this.myJournal = new MyJournal();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        renderMainPage();
    }

    // MODIFIES: this
    // EFFECTS: renders the loading screen
    // SOURCE: https://www.youtube.com/watch?v=pNup-WzHwRs
    private void renderLoadingScreen() {
        final JWindow loadingScreenWindow = initializeLoadingScreenWindow();
        JPanel imgPanel = renderImgPanel(loadingScreenWindow);
        addImg(imgPanel);
        addProgressBar(loadingScreenWindow);
        addTimer(loadingScreenWindow, progress);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: renders the image onto the loading screen window
    // SOURCE: https://www.youtube.com/watch?v=pNup-WzHwRs
    private JPanel renderImgPanel(JWindow loadingScreenWindow) {
        JPanel imgPanel = new JPanel();
        imgPanel.setBackground(Color.white);
        imgPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        loadingScreenWindow.add(imgPanel);

        return imgPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds and adjusts the image onto the panel
    // SOURCE: https://www.youtube.com/watch?v=pNup-WzHwRs
    // IMG SOURCE: https://dribbble.com/shots/5331825-Loading-XXI
    private void addImg(JPanel imgPanel) {
        String imagePath = "data/loading.gif";
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(FRAME_WIDTH,
                FRAME_HEIGHT,
                Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgPanel.add(imageLabel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the JWindow's properties for the loading screen
    // SOURCE: https://www.youtube.com/watch?v=pNup-WzHwRs
    private JWindow initializeLoadingScreenWindow() {
        final JWindow loadingScreenWindow = new JWindow(this);
        loadingScreenWindow.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        loadingScreenWindow.setLocationRelativeTo(null);
        loadingScreenWindow.setVisible(true);

        return loadingScreenWindow;
    }

    // MODIFIES: this
    // EFFECTS: adds a JProgressBar to show the application's loading progress
    // SOURCE: https://www.youtube.com/watch?v=pNup-WzHwRs
    private void addProgressBar(JWindow loadingScreen) {
        UIManager.put("ProgressBar.background", Color.white);
        UIManager.put("ProgressBar.foreground", Color.green);
        UIManager.put("ProgressBar.selectionBackground", Color.red);
        UIManager.put("ProgressBar.selectionForeground", Color.green);
        progress = new JProgressBar(0, 100);
        progress.setForeground(Color.green);
        loadingScreen.add(BorderLayout.PAGE_END, progress);
        loadingScreen.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: adds a timer to keep track of the loading time and when to switch to the main page
    // SOURCE: https://www.youtube.com/watch?v=pNup-WzHwRs
    private void addTimer(JWindow loadingScreen, JProgressBar progress) {
        timer = new Timer(100, e -> {
            int x = progress.getValue();
            if (x == 100) {
                loadingScreen.dispose();
                JournalAppGUI.this.setVisible(true);
                timer.stop();
            } else {
                progress.setValue(x + 4);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: renders the main home page for the app
    private void renderMainPage() {
        getContentPane().removeAll();
        getContentPane().revalidate();
        getContentPane().repaint();

        renderTitle();
        renderMenuPanel();
        renderEntryPanel();
        renderAddBtn();
    }

    // MODIFIES: this
    // EFFECTS: renders the title on the main page
    private void renderTitle() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_BLUE);
        addPadding(titlePanel, 40, 0, 0, 0);
        titleLbl = new JLabel("My Check-in App", JLabel.CENTER);
        titleLbl.setForeground(WHITE);
        titleLbl.setFont(new Font("Georgia", Font.BOLD, 40));
        titlePanel.add(titleLbl);
        add(titlePanel);
    }

    // MODIFIES: this
    // EFFECTS: renders the menu panel
    private void renderMenuPanel() {
        menuPanel = new JPanel(new GridLayout(1, 3, 60, 0));
        menuPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        addPadding(menuPanel, 0, PADDING, 0, PADDING);

        renderEntriesLabel();
        renderSaveLoadPanel();

        add(menuPanel);
    }

    // MODIFIES: this
    // EFFECTS: renders the "Your entries" JLabel
    private void renderEntriesLabel() {
        JLabel yourCardsLabel = new JLabel("Your entries");
        yourCardsLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        yourCardsLabel.setHorizontalAlignment(JLabel.LEFT);
        yourCardsLabel.setVerticalAlignment(JLabel.BOTTOM);
        menuPanel.add(yourCardsLabel);
    }

    // MODIFIES: this
    // EFFECTS: renders the save and load panel with corresponding buttons
    private void renderSaveLoadPanel() {
        saveLoadPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        saveLoadPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        renderSaveButton();
        renderLoadButton();
        menuPanel.add(saveLoadPanel);
    }

    // MODIFIES: this
    // EFFECTS: renders the load button and activates its event listener
    private void renderLoadButton() {
        JPanel loadBtnPanel = new JPanel();
        loadBtn = new JButton("Load entries");
        loadBtn.addActionListener(e -> {
            doLoadJournal();
            renderMainPage();
        });
        loadBtn.setHorizontalAlignment(JButton.CENTER);
        loadBtn.setVerticalAlignment(JButton.CENTER);
        loadBtn.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        loadBtnPanel.add(loadBtn);
        saveLoadPanel.add(loadBtnPanel);
    }

    // MODIFIES: this
    // EFFECTS: loads journal entries from file
    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void doLoadJournal() {
        try {
            this.myJournal = jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: renders the save button and activates its event listener
    private void renderSaveButton() {
        JPanel saveBtnPanel = new JPanel();
        saveBtn = new JButton("Save activities");
        saveBtn.addActionListener(e -> {
            doSaveEntries();
            renderMainPage();
        });
        saveBtn.setHorizontalAlignment(JButton.CENTER);
        saveBtn.setVerticalAlignment(JButton.CENTER);
        saveBtn.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        saveBtnPanel.add(saveBtn);
        saveLoadPanel.add(saveBtnPanel);
    }

    // EFFECTS: saves the journal entries to file
    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void doSaveEntries() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.myJournal);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: renders the entry panel to display all entries
    private void renderEntryPanel() {
        entryPanel = new JPanel(new GridLayout(0, 5, 25, 20));
        entryPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        addPadding(entryPanel, 0, PADDING, 0, PADDING);

        renderEntryPanels(entryPanel);
        add(entryPanel);
    }

    // MODIFIES: this
    // EFFECTS: renders each entry added to the journal
    private void renderEntryPanels(JPanel panel) {
        ArrayList<Entry> entries = myJournal.getAllEntries();

        for (Entry entry : entries) {
            JPanel entryPanel = renderEntryInfoPanel(entry);
            panel.add(entryPanel);
        }
    }

    // MODIFIES: this
    // EFFECTS: renders one entry
    private JPanel renderEntryInfoPanel(Entry entry) {
        JPanel entryPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        entryPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        entryPanel.setBackground(DARK_BLUE);

        renderEntryPanelContent(entry, entryPanel);

        return entryPanel;
    }

    // MODIFIES: this
    // EFFECTS: renders one entry's labels
    private void renderEntryPanelContent(Entry entry, JPanel entryPanel) {
        JLabel entryLbl;

        entryLbl = new JLabel(entry.getTitle());
        entryLbl.setForeground(WHITE);
        entryLbl.setHorizontalAlignment(JLabel.CENTER);
        entryLbl.setFont(new Font("Georgia", Font.BOLD, 14));

        entryPanel.add(entryLbl);
        renderEntryRemoveButton(entry, entryPanel);
        renderEntrySeeMoreButton(entry, entryPanel);
    }

    // MODIFIES: this
    // EFFECTS: renders one entry's remove button and initializes its event listener
    private void renderEntryRemoveButton(Entry entry, JPanel entryPanel) {
        JButton removeBtn;
        JPanel removeBtnPanel = new JPanel();
        removeBtnPanel.setBackground(DARK_BLUE);
        removeBtn = new JButton("Remove entry");
        removeBtnPanel.add(removeBtn);
        removeBtn.addActionListener(e -> {
            myJournal.deleteEntry(entry.getTitle());
            renderMainPage();
        });
        entryPanel.add(removeBtnPanel);
    }

    // MODIFIES: this
    // EFFECTS: renders one entry's see more button and initializes its event listener
    private void renderEntrySeeMoreButton(Entry entry, JPanel entryPanel) {
        JButton seeMoreBtn;
        JPanel seeMoreBtnPanel = new JPanel();
        seeMoreBtnPanel.setBackground(DARK_BLUE);
        seeMoreBtn = new JButton("See more");
        seeMoreBtnPanel.add(seeMoreBtn);
        seeMoreBtn.addActionListener(e -> renderSeeMorePopUp(entry));
        entryPanel.add(seeMoreBtnPanel);
    }

    // MODIFIES: this
    // EFFECTS: renders the see more entry window
    private void renderSeeMorePopUp(Entry entry) {
        seeMoreWindow = initializeSeeMoreWindow();
        renderSeeMoreWindow(seeMoreWindow, entry);
    }

    // MODIFIES: this
    // EFFECTS: initializes the JWindow properties of the see more pop-up
    private JWindow initializeSeeMoreWindow() {
        seeMoreWindow = new JWindow(this);
        seeMoreWindow.setFocusable(true);
        seeMoreWindow.setSize(SEE_MORE_WIDTH, SEE_MORE_HEIGHT);
        seeMoreWindow.setLocationRelativeTo(null);
        seeMoreWindow.setVisible(true);
        return seeMoreWindow;
    }

    // MODIFIES: this
    // EFFECTS: renders the JWindow for the see more window
    private void renderSeeMoreWindow(JWindow window, Entry entry) {
        JPanel entrySeeMorePanel = initializeSeeMoreEntryPanel();
        renderSeeMoreEntryPanelInfo(entrySeeMorePanel, entry);
        renderSeeMoreEntryPanelButtons(entrySeeMorePanel);
        window.add(entrySeeMorePanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the JPanel's properties for the see more pop-up
    private JPanel initializeSeeMoreEntryPanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 0, 0));
        formPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        addPadding(formPanel, 0, PADDING, 0, PADDING);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Entry info"));
        return formPanel;
    }

    // MODIFIES: this
    // EFFECTS: renders the text fields for the see more pop-up
    private void renderSeeMoreEntryPanelInfo(JPanel entrySeeMorePanel, Entry entry) {
        JLabel titleLbl = new JLabel(entry.getTitleAndDate());
        titleLbl.setFont(new Font("Georgia", Font.BOLD, 14));
        entrySeeMorePanel.add(titleLbl);

        JTextArea contentLbl = new JTextArea();
        contentLbl.setText(entry.getEntry().replace(entry.getTitleAndDate(), ""));
        contentLbl.setEditable(false);
        contentLbl.setLineWrap(true);
        contentLbl.setWrapStyleWord(true);
        contentLbl.setFont(new Font("Georgia", Font.PLAIN, 12));
        entrySeeMorePanel.add(new JScrollPane(contentLbl));
    }

    // MODIFIES: this
    // EFFECTS: renders the back button for the see more pop-up
    private void renderSeeMoreEntryPanelButtons(JPanel seeMorePanel) {
        backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            seeMoreWindow.dispose();
            JournalAppGUI.this.setVisible(true);
        });
        backBtn.setSize(new Dimension(50, 100));
        seeMorePanel.add(backBtn);
    }

    // MODIFIES: this
    // EFFECTS: renders the new entry button and activates its event listener
    private void renderAddBtn() {
        JPanel addBtnPanel = new JPanel();
        addBtn = new JButton("New entry");
        addBtn.addActionListener(e -> renderAddEntryForm());
        addBtn.setHorizontalAlignment(JButton.CENTER);
        addBtn.setVerticalAlignment(JButton.CENTER);
        addBtn.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        addBtnPanel.add(addBtn);
        add(addBtnPanel);
    }

    // MODIFIES: this
    // EFFECTS: renders the entry input form
    private void renderAddEntryForm() {
        addEntryFormWindow = initializeAddEntryForm();
        renderAddEntryFormWindow(addEntryFormWindow);
    }

    // MODIFIES: this
    // EFFECTS: initializes the JWindow properties of the entry input form
    private JWindow initializeAddEntryForm() {
        addEntryFormWindow = new JWindow(this);
        addEntryFormWindow.setFocusable(true);
        addEntryFormWindow.setSize(FORM_WIDTH, FORM_HEIGHT);
        addEntryFormWindow.setLocationRelativeTo(null);
        addEntryFormWindow.setVisible(true);
        return addEntryFormWindow;
    }

    // MODIFIES: this
    // EFFECTS: renders the JWindow for the entry input form
    private void renderAddEntryFormWindow(JWindow window) {
        JPanel addEntryFormPanel = initializeAddEntryFormPanel();
        renderAddEntryFormPanelInputs(addEntryFormPanel);
        renderAddEntryFormPanelButtons(addEntryFormPanel);
        window.add(addEntryFormPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the JPanel's properties for the journal input form
    private JPanel initializeAddEntryFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 40, 40));
        formPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        addPadding(formPanel, 0, PADDING, 0, PADDING);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "New entry"));
        return formPanel;
    }

    // MODIFIES: this
    // EFFECTS: renders the JButtons for the entry input form
    private void renderAddEntryFormPanelButtons(JPanel addEntryFormPanel) {
        renderAddEntryFormPanelCancelBtn(addEntryFormPanel);
        renderAddEntryFormPanelConfirmBtn(addEntryFormPanel);
    }

    // MODIFIES: this
    // EFFECTS: renders the confirm button for the entry input form
    private void renderAddEntryFormPanelConfirmBtn(JPanel addEntryFormPanel) {
        confirmBtn = new JButton("Add");
        confirmBtn.addActionListener(e -> {
            try {
                processUserInput();
                addEntryFormWindow.dispose();
                renderMainPage();
            } catch (IllegalInputException ex) {
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, "Please fill out all fields.");
            }
        });
        addEntryFormPanel.add(confirmBtn);
    }

    // MODIFIES: this
    // EFFECTS: creates a new entry with user input, throws IllegalInputException
    // if title, date, or content are blank
    private void processUserInput() throws IllegalInputException {
        String title = titleInput.getText();
        String date = dateInput.getText();
        String content = contentInput.getText();

        if (!checkNonZeroStringLength(title) || (!checkNonZeroStringLength(date))
                || (!checkNonZeroStringLength(content))) {
            throw new IllegalInputException();
        }

        Entry entry = new Entry(title, content, date);
        myJournal.addEntry(entry);
    }

    // EFFECTS: produces true if string has a non-zero length, else false
    private boolean checkNonZeroStringLength(String str) {
        return str.length() != 0;
    }

    // MODIFIES: this
    // EFFECTS: renders the cancel button for the entry input form
    private void renderAddEntryFormPanelCancelBtn(JPanel addEntryFormPanel) {
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> {
            addEntryFormWindow.dispose();
            JournalAppGUI.this.setVisible(true);
        });
        addEntryFormPanel.add(cancelBtn);
    }

    // MODIFIES: this
    // EFFECTS: renders the input fields for the entry input form
    private void renderAddEntryFormPanelInputs(JPanel addEntryFormPanel) {
        JLabel titleLbl = new JLabel("Title: ");
        addEntryFormPanel.add(titleLbl);

        titleInput = new JTextField(20);
        addEntryFormPanel.add(titleInput);

        JLabel dateLbl = new JLabel("Date: ");
        addEntryFormPanel.add(dateLbl);

        dateInput = new JTextField(20);
        addEntryFormPanel.add(dateInput);

        JLabel contentLbl = new JLabel("Content");
        addEntryFormPanel.add(contentLbl);

        contentInput = new JTextArea(40,20);
        contentInput.setLineWrap(true);
        contentInput.setWrapStyleWord(true);
        addEntryFormPanel.add(new JScrollPane(contentInput));
    }

    // MODIFIES: this
    // EFFECTS: adds padding to a given JPanel
    private void addPadding(JPanel panel, int top, int left, int bottom, int right) {
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    // EFFECTS: starts the application
    public static void main(String[] args) {
        new JournalAppGUI();
    }
}
