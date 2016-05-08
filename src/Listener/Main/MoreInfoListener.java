package Listener.Main;

import Entity.Activity;
import Repository.ActivityRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Display all information for the activity selected in the list
 */
public class MoreInfoListener implements ActionListener{
    private final JFrame frame;
    private final JTable listActivities;

    /**
     * Constructor
     *
     * @param frame
     * @param listActivities
     */
    public MoreInfoListener(JFrame frame, JTable listActivities) {
        super();
        this.frame = frame;
        this.listActivities = listActivities;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if an activity is selected
        if (listActivities.getSelectedRow()== -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une activité.");
            return;
        }

        // If an activity is selected -> Display all information
        DefaultTableModel activitiesModel = (DefaultTableModel) listActivities.getModel();
        String activityId = (String) activitiesModel.getValueAt(listActivities.getSelectedRow(), 0);
        try {
            Activity activity = new ActivityRepository().getActivityById(activityId);

            /*
             * Frame creation
             */
            JFrame frame = new JFrame("Informations complémentaires");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setSize(500, 600);
            frame.setLocationRelativeTo(null); // Centre la fenêtre
            frame.setResizable(false);

            /*
             * PANELS
             */
            JPanel activityPanel = new JPanel();
            activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));

            JPanel weatherPanel = new JPanel();
            weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS));


            /*
             * ACTIVITY PANEL
             */
            // Title
            JLabel titleActivity = new JLabel(activity.getName());
            titleActivity.setFont(new Font("Serif", Font.PLAIN, 25));
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            titlePanel.add(titleActivity);
            activityPanel.add(titlePanel);

            // Total time
            JLabel timeActivity = new JLabel("Durée: "+String.valueOf(activity.getTime().getTotalMinutes()) +"min");
            timeActivity.setFont(new Font("Serif", Font.PLAIN, 14));
            JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            timePanel.add(timeActivity);
            // Category
            JLabel categoryActivity = new JLabel("Catégorie: "+ activity.getCategory().getName());
            categoryActivity.setFont(new Font("Serif", Font.PLAIN, 14));
            JPanel catPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            catPanel.add(categoryActivity);
            // Rating
            JLabel ratingActivity = new JLabel("Cotation: "+ activity.getRating() +"/5");
            ratingActivity.setFont(new Font("Serif", Font.PLAIN, 14));
            JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            ratingPanel.add(ratingActivity);
            // Info line
            JPanel ratingTimeDescriptionPanel = new JPanel();
            ratingTimeDescriptionPanel.setLayout(new BoxLayout(ratingTimeDescriptionPanel, BoxLayout.X_AXIS));
            ratingTimeDescriptionPanel.add(timePanel);
            ratingTimeDescriptionPanel.add(catPanel);
            ratingTimeDescriptionPanel.add(ratingPanel);
            activityPanel.add(ratingTimeDescriptionPanel);

            // Description
            JTextArea description = new JTextArea(activity.getDescription());
            description.setSize(new Dimension(300, 300));
            description.setFont(new Font("Serif", Font.PLAIN, 16));
            description.setLineWrap(true);
            description.setEditable(false);
            JPanel descriptionPanel = new JPanel();
            descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.LINE_AXIS));
            descriptionPanel.add(new JScrollPane(description));
            activityPanel.add(descriptionPanel);


            /*
             * WEATHER PANEL
             */
            String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?q="+ activity.getCity().getName() +"&apikey=00c682a40aa417713f53449f4e1804de&units=metric";
            Map<String, String> weatherInfo = getWeatherInfo(weatherUrl);

            // Title
            JLabel weatherTitle = new JLabel("Météo à "+ activity.getCity().getName());
            weatherTitle.setFont(new Font("Serif", Font.PLAIN, 20));
            JPanel weatherTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            weatherTitlePanel.add(weatherTitle);
            weatherPanel.add(weatherTitlePanel);

            // Weather info
            JTextArea weatherDesc = new JTextArea("Il fait actuellement "+ weatherInfo.get("desc") +".\nLa température est actuellement de "+ weatherInfo.get("temp") +"°C . Les températures maximale et minimale prévue aujourd'hui sont de "+ weatherInfo.get("max_temp") +"°C et de "+ weatherInfo.get("min_temp") +"°C. L'humidité est de "+ weatherInfo.get("humidity") +"%.");
            weatherDesc.setLineWrap(true);
            weatherDesc.setEditable(false);
            JPanel weatherDescPanel = new JPanel();
            weatherDescPanel.setLayout(new BoxLayout(weatherDescPanel, BoxLayout.LINE_AXIS));
            weatherDescPanel.add(new JScrollPane(weatherDesc));
            weatherPanel.add(weatherDescPanel);


            frame.add(activityPanel, BorderLayout.NORTH);
            frame.add(weatherPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (SQLException | ClassNotFoundException | IOException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
    }

    /**
     * Read the text get from the  connection
     *
     * @param rd The reader
     * @return the all string
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Read the json from the url
     *
     * @param url The url on which we'll get the json object
     * @return The JSON object
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    /**
     * Get the weather info of the selected activity
     *
     * @param url The url on which we'll get the json object
     * @return return a ArrayMap of essential information
     * @throws IOException
     * @throws JSONException
     */
    public static Map<String, String> getWeatherInfo(String url) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl(url);
        Map<String, String> info = new HashMap<>();
        JSONObject main = (JSONObject) json.get("main");
        info.put("temp", String.valueOf(main.get("temp")));
        info.put("max_temp", String.valueOf(main.get("temp_max")));
        info.put("min_temp", String.valueOf(main.get("temp_min")));
        info.put("humidity", String.valueOf(main.get("humidity")));

        JSONArray weather = (JSONArray) json.get("weather");
        JSONObject weatherJson = (JSONObject) weather.get(0);
        info.put("desc", weatherJson.getString("description"));
        return info;
    }
}
