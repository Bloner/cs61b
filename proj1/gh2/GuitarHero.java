package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static final double CONCERT_A = 440.0;

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        GuitarString[] string = new GuitarString[37];
        for (int i = 0; i < 37; i++) {
            string[i] = new GuitarString(CONCERT_A *
                    Math.pow(2, (i - 24.0) / 12.0));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) != -1) {
                    string[keyboard.indexOf(key)].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < 37; i++) {
                sample += string[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < 37; i++) {
                string[i].tic();
            }
        }
    }
}
