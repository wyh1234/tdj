package tools.extend;


import android.graphics.drawable.Drawable;

import android.text.TextUtils;
import android.text.style.ImageSpan;


import com.base.utils.ColorUtil;
import com.base.utils.DensityUtil;
import com.base.utils.MySpecialStyle;
import com.zyinux.specialstring.relase.SpecialStringBuilder;



import cn.com.taodaji.R;



import static com.base.utils.UIUtils.getResources;

public class SimpleStringUtils {
    public static SpecialStringBuilder getTitleName(String name,String nickName,int productType,int productCriteria,int isP){

        MySpecialStyle style=new MySpecialStyle();
        SpecialStringBuilder sb=new SpecialStringBuilder();



            int size= DensityUtil.sp2px(16f)+4;

        //是否是热销
        if (productType == 3||productType == 4) {
            Drawable drawable_rexiao;
            if (productType == 3){
                 drawable_rexiao = getResources().getDrawable(R.mipmap.rexiao);
            }else {
                drawable_rexiao = getResources().getDrawable(R.mipmap.bao);

            }

            drawable_rexiao.setBounds(0, 0, size, size);
            ImageSpan imageSpan_rexiao = new ImageSpan(drawable_rexiao);


            style.setImage(imageSpan_rexiao);
            sb.append("1",style);
        }
        sb.append(" ",style);
        //商品标准“1”：通货商品 “2”：精品商品 '
        if (productCriteria == 2) {
            Drawable drawable_jin = getResources().getDrawable(R.mipmap.icon_jin_red);
            drawable_jin.setBounds(0, 0, size, size);
            ImageSpan imageSpan_jin = new ImageSpan(drawable_jin);

            style.setImage(imageSpan_jin);
            sb.append("2",style);
            sb.append(" ",style);
           // spannableString.setSpan(imageSpan_jin, 1, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE  );
        } else if (productCriteria == 1) {
            Drawable drawable_tong = getResources().getDrawable(R.mipmap.icon_tong_blue);
            drawable_tong.setBounds(0, 0, size, size);//drawable_tong.getIntrinsicWidth(), drawable_tong.getIntrinsicHeight()
            ImageSpan imageSpan_tong = new ImageSpan(drawable_tong);

            style.setImage(imageSpan_tong);
            sb.append("2",style);
            sb.append(" ",style);
        }

        //零售 0   整件批  1
        if (isP==1) {
            style.setColor(ColorUtil.getColor(R.color.orange_yellow_ff7d01));
            sb.append("P",style);
            sb.append(" ",style);
        }

        style.setColor(ColorUtil.getColor(R.color.black_4b));
        style.setAbsoluteSizeStyle(DensityUtil.sp2px(16f));
        sb.append(name,style);


        if (!TextUtils.isEmpty(nickName)) {
            style.setColor(ColorUtil.getColor(R.color.gray_68));
            style.setAbsoluteSizeStyle(DensityUtil.sp2px(14f));
            sb.append("("+nickName+")",style);
            // textView.setText(spannableString);
        }


        return sb;
    }


    public static SpecialStringBuilder getTitleNames(String name,String nickName,int productType,int productCriteria,int isP){

        MySpecialStyle style=new MySpecialStyle();
        SpecialStringBuilder sb=new SpecialStringBuilder();



        int size= DensityUtil.sp2px(16f)+4;

        //是否是热销
        if (productType == 3) {
            Drawable drawable_rexiao = getResources().getDrawable(R.mipmap.rexiao_bg);
            drawable_rexiao.setBounds(0, 0, size, size);
            ImageSpan imageSpan_rexiao = new ImageSpan(drawable_rexiao);


            style.setImage(imageSpan_rexiao);
            sb.append("1",style);
        }
        sb.append(" ",style);
        //商品标准“1”：通货商品 “2”：精品商品 '
        if (productCriteria == 2) {
            Drawable drawable_jin = getResources().getDrawable(R.mipmap.jing_bg);
            drawable_jin.setBounds(0, 0, size, size);
            ImageSpan imageSpan_jin = new ImageSpan(drawable_jin);

            style.setImage(imageSpan_jin);
            sb.append("2",style);
            sb.append(" ",style);
            // spannableString.setSpan(imageSpan_jin, 1, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE  );
        } else if (productCriteria == 1) {
            Drawable drawable_tong = getResources().getDrawable(R.mipmap.tong_bg);
            drawable_tong.setBounds(0, 0, size, size);//drawable_tong.getIntrinsicWidth(), drawable_tong.getIntrinsicHeight()
            ImageSpan imageSpan_tong = new ImageSpan(drawable_tong);

            style.setImage(imageSpan_tong);
            sb.append("2",style);
            sb.append(" ",style);
        }

        //零售 0   整件批  1
        if (isP==1) {
            style.setColor(ColorUtil.getColor(R.color.orange_yellow_ff7d01));
            sb.append("P",style);
            sb.append(" ",style);
        }

        style.setColor(ColorUtil.getColor(R.color.black_4b));
        style.setAbsoluteSizeStyle(DensityUtil.sp2px(16f));
        sb.append(name,style);


        if (!TextUtils.isEmpty(nickName)) {
            style.setColor(ColorUtil.getColor(R.color.gray_68));
            style.setAbsoluteSizeStyle(DensityUtil.sp2px(14f));
            sb.append("("+nickName+")",style);
            // textView.setText(spannableString);
        }


        return sb;
    }


}
