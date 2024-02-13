package com.utils;

import com.driver.PlaywrightManager;
import com.exceptions.TestException;
import com.microsoft.playwright.Page.ScreenshotOptions;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class CaptureUtils extends ScreenRecorder {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    // Record with Monte Media library
    private static ScreenRecorder screenRecorder;
    private String name;

    public CaptureUtils(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    // Start record video
    public static void startRecord(String methodName) {
        File file = new File(SystemUtils.getCurrentDir() + "media/VideoRecords/");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        try {
            screenRecorder = new CaptureUtils(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null, file, methodName);
            screenRecorder.start();
        } catch (IOException | AWTException e) {
            throw new TestException("Unable to start recording.");
        }
    }

    // Stop record video
    public static void stopRecord() {
        try {
            screenRecorder.stop();
        } catch (IOException e) {
            throw new TestException("Unable to stop recording");
        }
    }

    public static void takeScreenshot(String screenshotName) {
        try {
            // Create directory if it doesn't exist
            String directoryPath = SystemUtils.getCurrentDir() + "media/Screenshots/";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Define screenshot options
            ScreenshotOptions screenshotOptions = new ScreenshotOptions();

            // Take screenshot and save to the specified path
            String filePath = Paths.get(directoryPath, screenshotName + "_" + dateFormat.format(new Date()) + ".png").toString();
            PlaywrightManager.getPage().screenshot(screenshotOptions.setPath(Paths.get(filePath)));

            // Print confirmation messages
            System.out.println("Screenshot taken: " + screenshotName);
            System.out.println("Screenshot taken current URL: " + PlaywrightManager.getPage().url());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {

        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        return new File(movieFolder, name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }
}
