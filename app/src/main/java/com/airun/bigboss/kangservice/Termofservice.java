package com.airun.bigboss.kangservice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class Termofservice extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Syarat dan Ketentuan")
                .setMessage("1.Biaya pembelian spare part ditetapkan melalui kesepakatan antara konsumen dan teknisi.\n\n2.Jika prediksi biaya total dirasa tidak sesuai, konsumen dapat membatalkan pekerjaan dan diharuskan membayar 50% dari tarif jasa sebagai upah inspeksi.\n\n3.Garansi layanan berlaku dalam 14 hari dihitung sejak tanggal selesainya pekerjaan.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
