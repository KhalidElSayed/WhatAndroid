package what.status;

import what.gui.ActivityNames;
import what.gui.Cancelable;
import what.gui.ErrorToast;
import what.gui.MyActivity2;
import what.gui.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import api.whatstatus.WhatStatus;

public class WhatStatusActivity extends MyActivity2 {
	private ImageView siteStatus, trackerStatus, ircStatus;
	private WhatStatus whatStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.setActivityName(ActivityNames.STATUS);
		super.requestIndeterminateProgress();
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.whatstatus);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void load() {
		siteStatus = (ImageView) this.findViewById(R.id.sitestatus);
		trackerStatus = (ImageView) this.findViewById(R.id.trackerstatus);
		ircStatus = (ImageView) this.findViewById(R.id.ircstatus);
	}

	@Override
	public void prepare() {
		setActionBarTitle("WhatStatus");
		new Load().execute();
	}

	public void populateLayout() {
		switch (whatStatus.getStatus().getSite()) {
			case 0:
				siteStatus.setImageResource((R.drawable.site_up));
				break;
			case 1:
				siteStatus.setImageResource((R.drawable.site_down));
				break;
			case 2:
				siteStatus.setImageResource((R.drawable.site_maintenance));
				break;
			default:
				siteStatus.setImageResource((R.drawable.site_up));
				break;
		}
		switch (whatStatus.getStatus().getTracker()) {
			case 0:
				trackerStatus.setImageResource((R.drawable.site_up));
				break;
			case 1:
				trackerStatus.setImageResource((R.drawable.site_down));
				break;
			case 2:
				trackerStatus.setImageResource((R.drawable.site_maintenance));
				break;
			default:
				trackerStatus.setImageResource((R.drawable.site_up));
				break;
		}
		switch (whatStatus.getStatus().getIrc()) {
			case 0:
				ircStatus.setImageResource((R.drawable.site_up));
				break;
			case 1:
				ircStatus.setImageResource((R.drawable.site_up));
				break;
		}
		/*
		 * try { for (int i = 0; i < status.getTweets().size(); i++) { tweetList.add(new TextView(this));
		 * tweetList.get(i).setTextSize(18); tweetList.get(i).setText(status.getTweets().get(i).getA() + "\n \t" +
		 * status.getTweets().get(i).getB()); twitterScroll.addView(tweetList.get(i)); } } catch (TwitterException e) {
		 * Toast.makeText(this, "Could not load tweets", Toast.LENGTH_LONG).show(); e.printStackTrace(); }
		 */
	}

	/*
	 * public void openTwitter(View v) { String url = "http://twitter.com/#!/whatcd"; Intent i = new
	 * Intent(Intent.ACTION_VIEW); i.setData(Uri.parse(url)); startActivity(i); }
	 */

	/*
	 * public void openWhatStatus(View v) { String url = "http://whatstatus.info/"; Intent i = new
	 * Intent(Intent.ACTION_VIEW); i.setData(Uri.parse(url)); startActivity(i); }
	 */

	private class Load extends AsyncTask<Void, Void, Boolean> implements Cancelable {
		public Load() {
			attachCancelable(this);
		}

		@Override
		public void cancel() {
			super.cancel(true);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				whatStatus = WhatStatus.init();
				// TODO always returns true until a way to get request status can be implement into whatstatus.info
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}

		@Override
		protected void onPostExecute(Boolean status) {
			hideIndeterminateProgress();
			if (status == true) {
				populateLayout();
			}
			unlockScreenRotation();
			if (status == false) {
				ErrorToast.show(WhatStatusActivity.this, WhatStatusActivity.class);
			}
		}

	}

}
