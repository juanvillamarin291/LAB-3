import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Cronometro extends JFrame {
    private JLabel horaLabel;
    private JLabel alarmaLabel;
    private final Timer timer;
    private int segundosTranscurridos;
    private int segundosAlarma;
    private int segundosDesdeAlarma;
    
    public Cronometro() {
        super("Cronómetro");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        horaLabel = new JLabel("00:00:00");
        horaLabel.setFont(new Font("Arial", Font.BOLD, 40));
        horaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        alarmaLabel = new JLabel("Alarma no establecida");
        alarmaLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        alarmaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(horaLabel, BorderLayout.CENTER);
        panel.add(alarmaLabel, BorderLayout.SOUTH);     
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        segundosAlarma = 0;
        segundosDesdeAlarma = 0;
        segundosTranscurridos = 0;
        timer = new Timer(1000, (ActionEvent e) -> {
            segundosTranscurridos++;
            String hora = new SimpleDateFormat("HH:mm:ss").format(new Date(segundosTranscurridos * 1000));
            horaLabel.setText(hora);
            
            if(segundosTranscurridos >= segundosAlarma && segundosAlarma > 0) {
                alarmaLabel.setText("¡ALARMA!");
                
                segundosDesdeAlarma++;
                if(segundosDesdeAlarma % 10 == 0) {
                    reproducirSonido();
                }
            }
        });
        
        timer.start();
    }
     public void establecerAlarma(int minutos, int segundos) {
        segundosAlarma = minutos * 60 + segundos;
        alarmaLabel.setText("Alarma establecida en " + minutos + "m " + segundos + "s");
    }
    private void reproducirSonido() {
        try {
            Clip sonido = AudioSystem.getClip();
            sonido.open(AudioSystem.getAudioInputStream(getClass().getResource("C:\\Users\\Estudiante\\Downloads/sonidoalarma.mp3")));
            sonido.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
        }
    }
    public static void main(String[] args) {
        Cronometro cronometro = new Cronometro();
        cronometro.establecerAlarma(0, 10); 
    }
}