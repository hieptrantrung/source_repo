import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private Button captureButton;
    private ImageView showImageView;
    protected String imageFilePath;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);
        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

        showImageView = (ImageView) findViewById(R.id.imageView1);

        preview.addView(mCameraPreview);

        captureButton = (Button) findViewById(R.id.button_capture);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, jpegCallback);
            }
        });

    }

    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();

        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    /** The jpeg callback. */
    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            FileOutputStream outStream = null;
            try {
                File miDirs = new File(
                        Environment.getExternalStorageDirectory() + "/myphotos");
                if (!miDirs.exists())
                    miDirs.mkdirs();

                final Calendar c = Calendar.getInstance();
                String new_Date = c.get(Calendar.DAY_OF_MONTH) + "-"
                        + ((c.get(Calendar.MONTH)) + 1) + "-"
                        + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR)
                        + "-" + c.get(Calendar.MINUTE) + "-"
                        + c.get(Calendar.SECOND);

                imageFilePath = String.format(
                        Environment.getExternalStorageDirectory() + "/myphotos"
                                + "/%s.jpg", "te1t(" + new_Date + ")");

                Uri selectedImage = Uri.parse(imageFilePath);
                File file = new File(imageFilePath);
                String path = file.getAbsolutePath();
                Bitmap bitmap = null;

                outStream = new FileOutputStream(file);
                outStream.write(data);
                outStream.close();

                if (path != null) {
                    if (path.startsWith("content")) {
                        bitmap = decodeStrem(file, selectedImage,
                                CameraActivity.this);
                    } else {
                        bitmap = decodeFile(file, 10);
                    }
                }
                if (bitmap != null) {
                    showImageView.setImageBitmap(bitmap);
                    Toast.makeText(CameraActivity.this,
                            "Picture Captured Successfully:", Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(CameraActivity.this,
                            "Failed to Capture the picture. kindly Try Again:",
                            Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }

    };

    /**
     * Decode strem.
     * 
     * @param fil
     *            the fil
     * @param selectedImage
     *            the selected image
     * @param mContext
     *            the m context
     * @return the bitmap
     */
    public static Bitmap decodeStrem(File fil, Uri selectedImage,
            Context mContext) {

        Bitmap bitmap = null;
        try {

            bitmap = BitmapFactory.decodeStream(mContext.getContentResolver()
                    .openInputStream(selectedImage));

            final int THUMBNAIL_SIZE = getThumbSize(bitmap);

            bitmap = Bitmap.createScaledBitmap(bitmap, THUMBNAIL_SIZE,
                    THUMBNAIL_SIZE, false);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos
                    .toByteArray()));

            return bitmap = rotateImage(bitmap, fil.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Rotate image.
     * 
     * @param bmp
     *            the bmp
     * @param imageUrl
     *            the image url
     * @return the bitmap
     */
    public static Bitmap rotateImages(Bitmap bmp, String imageUrl) {
        if (bmp != null) {
            ExifInterface ei;
            int orientation = 0;
            try {
                ei = new ExifInterface(imageUrl);
                orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

            } catch (IOException e) {
                e.printStackTrace();
            }
            int bmpWidth = bmp.getWidth();
            int bmpHeight = bmp.getHeight();
            Matrix matrix = new Matrix();
            switch (orientation) {
            case ExifInterface.ORIENTATION_UNDEFINED:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                break;
            }
            Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmpWidth,
                    bmpHeight, matrix, true);
            return resizedBitmap;
        } else {
            return bmp;
        }
    }

    /**
     * Decode file.
     * 
     * @param f
     *            the f
     * @param sampling
     *            the sampling
     * @param check
     *            the check
     * @return the bitmap
     */
    public static Bitmap decodeFile(File f, int sampling) {
        try {
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(
                    new FileInputStream(f.getAbsolutePath()), null, o2);

            o2.inSampleSize = sampling;
            o2.inTempStorage = new byte[48 * 1024];

            o2.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(
                    new FileInputStream(f.getAbsolutePath()), null, o2);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            bitmap = rotateImage(bitmap, f.getAbsolutePath());
            return bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Rotate image.
     * 
     * @param bmp
     *            the bmp
     * @param imageUrl
     *            the image url
     * @return the bitmap
     */
    public static Bitmap rotateImage(Bitmap bmp, String imageUrl) {
        if (bmp != null) {
            ExifInterface ei;
            int orientation = 0;
            try {
                ei = new ExifInterface(imageUrl);
                orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

            } catch (IOException e) {
                e.printStackTrace();
            }
            int bmpWidth = bmp.getWidth();
            int bmpHeight = bmp.getHeight();
            Matrix matrix = new Matrix();
            switch (orientation) {
            case ExifInterface.ORIENTATION_UNDEFINED:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                break;
            }
            Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmpWidth,
                    bmpHeight, matrix, true);
            return resizedBitmap;
        } else {
            return bmp;
        }
    }


    /**
     * Gets the thumb size.
     * 
     * @param bitmap
     *            the bitmap
     * @return the thumb size
     */
    public static int getThumbSize(Bitmap bitmap) {

        int THUMBNAIL_SIZE = 250;
        if (bitmap.getWidth() < 300) {
            THUMBNAIL_SIZE = 250;
        } else if (bitmap.getWidth() < 600) {
            THUMBNAIL_SIZE = 500;
        } else if (bitmap.getWidth() < 1000) {
            THUMBNAIL_SIZE = 750;
        } else if (bitmap.getWidth() < 2000) {
            THUMBNAIL_SIZE = 1500;
        } else if (bitmap.getWidth() < 4000) {
            THUMBNAIL_SIZE = 2000;
        } else if (bitmap.getWidth() > 4000) {
            THUMBNAIL_SIZE = 2000;
        }
        return THUMBNAIL_SIZE;
    }

}