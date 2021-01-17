//library
public static interface OnColorChangedListener
{
	public void onColorChanged(ColorPicker picker, int color);
}
class ColorPicker extends LinearLayout
{ 
	private SeekBar r;
	private SeekBar g;
	private SeekBar b;
	private EditText hex;
	private ImageView icon;
	private LinearLayout color_type;
	private SeekBar.OnSeekBarChangeListener listener;
	private OnColorChangedListener l;
	public ColorPicker(Context c)
	{
		super(c);
		init();
	}
	
	private void init(){
		
		listener = new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar p1, int p2, boolean p3)
			{
				int color = Color.rgb(r.getProgress(), g.getProgress(), b.getProgress());
				String temp = String.format("%1$08x", color); //sketchware: TODO "%1\$0x"
				String result = temp.substring(2);
				hex.setText("#" + result);
				hex.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
				
				icon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
				color_type.setBackgroundColor(color);
				
				if(l != null) l.onColorChanged(ColorPicker.this, color);
			}
			@Override public void onStartTrackingTouch(SeekBar p1){}
			@Override public void onStopTrackingTouch(SeekBar p1){}
		};
		LinearLayout lay2 = new LinearLayout(getContext());
		lay2.setOrientation(VERTICAL);
		lay2.setPadding(8, 0, 8, 8);
		lay2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		hex = new EditText(getContext());
		
		icon = new
		ImageView(getContext());
		
		color_type = new
		LinearLayout(getContext());
		ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(16, 0, 16, 0);
		color_type.setLayoutParams(new ViewGroup.LayoutParams(700, 200));
		icon.setPadding(8, 8, 8, 8);
		color_type.setPadding(8, 8, 8, 8);
		
		icon.setLayoutParams(params);
		
		hex.setLayoutParams(params);
		hex.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_DONE);
		hex.setText("#9e9e9e");
		hex.setOnEditorActionListener(new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView text, int code, KeyEvent event)
			{
				try {
					int color = Color.parseColor(text.getText().toString());
					r.setProgress(Color.red(color));
					g.setProgress(Color.green(color));
					b.setProgress(Color.blue(color));
				} catch(Exception e){
					Toast.makeText(getContext(), "Color code is wrong", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		lay2.addView(icon);
		icon.setImageResource(R.drawable.default_image);
		
		lay2.addView(color_type);
		lay2.addView(hex);
		r = new SeekBar(getContext());
		setProgressColor(r, 0xffcc5577);
		r.setMax(255);
		r.setOnSeekBarChangeListener(listener);
		lay2.addView(r);
		g = new SeekBar(getContext());
		setProgressColor(g, 0xff339977);
		g.setMax(255);
		g.setOnSeekBarChangeListener(listener);
		lay2.addView(g);
		b = new SeekBar(getContext());
		setProgressColor(b, 0xff6077bb);
		b.setMax(255);
		b.setOnSeekBarChangeListener(listener);
		lay2.addView(b);
		addView(lay2);
		int color = Color.parseColor(hex.getText().toString());
		r.setProgress(Color.red(color));
		g.setProgress(Color.green(color));
		b.setProgress(Color.blue(color));
		icon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
		color_type.setBackgroundColor(color);
	}
	public void setColor(int color)
	{
		hex.setText("#" + String.format("%1$08x", color).substring(2));
		r.setProgress(Color.red(color));
		g.setProgress(Color.green(color));
		b.setProgress(Color.blue(color));
	}
	public int getColor(boolean refreshFromSlider)
	{
		if(refreshFromSlider) listener.onProgressChanged(null, 0, false);
		return Color.parseColor(hex.getText().toString());
	}
	public int getColor()
	{
		return getColor(true);
	}
	public void setOnColorChangedListener(OnColorChangedListener l)
	{
		this.l = l;
	}
	private void setProgressColor(AbsSeekBar bar, int color)
	{
		bar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN); bar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);
	}
}
