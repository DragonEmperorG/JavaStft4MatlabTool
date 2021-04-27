package cn.edu.whu.unsc;

import java.io.*;

public class Main {

    private static int STFT_HOP_SIZE = 128;
    private static int STFT_HOP_SIZE_MINUS_ONE = STFT_HOP_SIZE - 1;

    private int AUDIO_SAMPLE_RATE = 48000;
    private int STFT_FFT_SIZE = 1024;

    public static void main(String[] args) {
        // write your code here

        String inputAudioWavFilePath = "D:\\GithubRepositories\\JavaStft4MatlabTool\\asset\\Audio.wav";
        String inputFilePath = "D:\\GithubRepositories\\JavaStft4MatlabTool\\asset\\Testcase_DEQing_202104252005.csv";
        String outputFilePath = "D:\\GithubRepositories\\JavaStft4MatlabTool\\asset\\Testcase_DEQing_202104252005_Stft.csv";

//        try {
//            // Open the wav file specified as the first argument
//            File file = new File(inputAudioWavFilePath);
//
//            WavFile wavFile = WavFile.openWavFile(file);
//
//            // Display information about the wav file
//            wavFile.display();
//
//            // Get the number of audio channels in the wav file
//            int numChannels = wavFile.getNumChannels();
//
//            // Create a buffer of 100 frames
//            double[] buffer = new double[STFT_HOP_SIZE * numChannels];
//
//            int framesRead;
//            double min = Double.MAX_VALUE;
//            double max = Double.MIN_VALUE;
//
//            do {
//                // Read frames into buffer
//                framesRead = wavFile.readFrames(buffer, STFT_HOP_SIZE);
//
//                // Loop through frames and look for minimum and maximum value
//                for (int s = 0; s < framesRead * numChannels; s++) {
//                    if (buffer[s] > max) max = buffer[s];
//                    if (buffer[s] < min) min = buffer[s];
//                }
//            }
//            while (framesRead != 0);
//
//            // Close the wavFile
//            wavFile.close();
//
//            // Output the minimum and maximum value
//            System.out.printf("Min: %f, Max: %f\n", min, max);
//        } catch (Exception e) {
//            System.err.println(e);
//        }

        try {
            FileInputStream fileInputStream = new FileInputStream(inputFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(fileInputStream));


            int audioStreamCounter = 0;
            int audioStftCounter = 0;
            String line;
            StringBuilder stringBuilder;
            double[] slicedAudioStream4Stft = new double[STFT_HOP_SIZE];
            STFT stftAnalyzer = new STFT();
            while ((line = buffReader.readLine()) != null) {
                slicedAudioStream4Stft[audioStreamCounter % STFT_HOP_SIZE] = Double.parseDouble(line);
                if (audioStreamCounter % STFT_HOP_SIZE == STFT_HOP_SIZE_MINUS_ONE) {
                    stftAnalyzer.feedData(slicedAudioStream4Stft, STFT_HOP_SIZE);
                    double[] spectrumAmpDB4StftAnalyzer = stftAnalyzer.getSpectrumAmpDB();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(audioStftCounter);
                    for (int i = 0; i < 513; i++) {
                        stringBuilder.append(",").append(spectrumAmpDB4StftAnalyzer[i]);
                    }
                    stringBuilder.append("\n");
                    fileOutputStream.write(stringBuilder.toString().getBytes());
                    audioStftCounter++;
                }
                audioStreamCounter++;
            }

            fileInputStream.close();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
