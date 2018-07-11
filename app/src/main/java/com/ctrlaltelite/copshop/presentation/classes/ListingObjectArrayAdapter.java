package com.ctrlaltelite.copshop.presentation.classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctrlaltelite.copshop.R;
import com.ctrlaltelite.copshop.application.CopShopHub;
import com.ctrlaltelite.copshop.presentation.activities.ListingViewActivity;
import com.ctrlaltelite.copshop.presentation.utilities.ImageUtility;
import com.ctrlaltelite.copshop.presentation.utilities.StringUtility;
import com.ctrlaltelite.copshop.objects.ListingObject;
import java.util.List;

public class ListingObjectArrayAdapter extends ArrayAdapter<ListingObject> {
    private Context context;
    private Activity activity;
    private List<ListingObject> listingInfo;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    String imageData[];
    View view;

    public ListingObjectArrayAdapter(Context context, List<ListingObject> objects) {
        super(context, 0, objects);
        this.context = context;
        this.activity = (Activity)context;
        this.listingInfo = objects;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the listing to display
        ListingObject info = listingInfo.get(position);

        // Inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listing_row, null);

        TextView title = (TextView) view.findViewById(R.id.listing_list_title);
        TextView seller = (TextView) view.findViewById(R.id.listing_list_seller);
        TextView description = (TextView) view.findViewById(R.id.listing_list_description);
        TextView price = (TextView) view.findViewById(R.id.listing_list_price);
        TextView bids = (TextView) view.findViewById(R.id.listing_list_bid_count);
        TextView timeLeft = (TextView) view.findViewById(R.id.listing_list_time_left);
        TextView timeLeftLabel = (TextView) view.findViewById(R.id.listing_list_time_left_label);
        ImageView image = (ImageView) view.findViewById(R.id.listing_list_image);

        // Trim and set the description and other fields
        title.setText(info.getTitle());
        description.setText(StringUtility.ellipsize(info.getDescription(), 70));
        seller.setText(CopShopHub.getListingService().getSellerNameFromListing(info.getId()));

        // Get the image associated with this listing
        if (!info.getImageData().isEmpty() && info.getImageData() != "") {
            imageData = ImageUtility.imageDecode(info.getImageData());
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                int rotationAmount = Integer.parseInt(imageData[0]);
                Uri imageUri = Uri.parse(imageData[1]);
                Bitmap bm = ImageUtility.uriToBitmap(context, imageUri);
                bm = ImageUtility.rotateBitmap(bm, rotationAmount);
                image.setImageBitmap(bm);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

        // Hide price and bid counter until bidding is implemented
        bids.setText(CopShopHub.getListingService().getNumBids(info.getId()) + " bids");
        price.setText("$" + CopShopHub.getListingService().getNextBidTotal(info) + ",");

        // Hide timer until auction timers are implemented
//        timeLeft.setText(info.getTimeLeft());
        timeLeftLabel.setVisibility(View.INVISIBLE);
        timeLeft.setVisibility(View.INVISIBLE);

        return view;
    }
}