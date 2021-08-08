package com.teacher.modern.modernteacher.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.teacher.modern.modernteacher.R;
import com.teacher.modern.modernteacher.connectivity.ServiceGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link New.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link New#newInstance} factory method to
 * create an instance of this fragment.
 */
public class New extends Fragment implements OnPageChangeListener,OnLoadCompleteListener {

    // private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SAMPLE_FILE = "sample_pdf.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    ProgressBar mProgressDialog;
    String year;

    int permissionGranted = 0;

    View rootView;

    Fragment currentFragment;
    FragmentTransaction transaction;
    FragmentManager manager;

    public New() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.academic_calander);
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = getActivity().checkCallingOrSelfPermission(permission);

        if(res==PackageManager.PERMISSION_GRANTED){

        }
        else {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        }

        String permission1 = "android.permission.READ_EXTERNAL_STORAGE";
        int res1 = getActivity().checkCallingOrSelfPermission(permission);

        if(res1==PackageManager.PERMISSION_GRANTED){

        }
        else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2);

        }

         year = Calendar.getInstance().get(Calendar.YEAR) + "";

        pdfView= (PDFView)rootView.findViewById(R.id.pdfView);

        displayFromAsset("sample_pdf.pdf");

        return rootView;
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        File sdcard = Environment.getExternalStorageDirectory();


        File file = new File(sdcard,"pdf_file1.pdf");

        /*File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/sdcard/pdf_file.pdf");*/
        if (file.exists()) {
            pdfView.fromFile(file)
                    .defaultPage(pageNumber)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(getActivity()))
                    .load();
        }
        else{


            String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
            int res = getActivity().checkCallingOrSelfPermission(permission);

            if(res==PackageManager.PERMISSION_GRANTED){
                DownloadTask downloadTask = new DownloadTask(getActivity());
                downloadTask.execute(ServiceGenerator.websiteUrl+"AcademicCalenderContent/"+year+".pdf");


            }
            else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

            }





        }

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        //setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
//            Toast.makeText(context,"I'm here..",Toast.LENGTH_LONG).show();
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {



                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/pdf_file1.pdf");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            //mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
           /* mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);*/
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            // mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();

                manager = getFragmentManager();
                currentFragment = new New();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment,currentFragment);
                transaction.addToBackStack("New");
                transaction.commit();

            }

        }
    }


}
