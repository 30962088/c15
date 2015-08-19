package com.cctv.music.cctv15.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cctv.music.cctv15.BaseActivity;
import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.ZoneActivity;
import com.cctv.music.cctv15.model.Sex;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.IsHaveUserNameRequest;
import com.cctv.music.cctv15.network.SetClientUserRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.AliyunUtils;
import com.cctv.music.cctv15.utils.CropImageUtils;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.Serializable;

public class FillUserFragment extends BaseFragment implements View.OnClickListener, BaseActivity.OnGallerySelectionListener, AliyunUtils.UploadListener, BaseActivity.OnCitySelectionListener {

    public static FillUserFragment newInstance(Model model) {
        FillUserFragment fragment = new FillUserFragment();
        Bundle args = new Bundle();
        args.putSerializable("model", model);
        fragment.setArguments(args);
        return fragment;
    }


    public static class Model implements Serializable {
        private int usertype;
        private String sid;
        private String avatar;
        private String city;
        private Sex sex;
        private String username;
        private String password;


        public Model(int usertype, String sid, String avatar, String city, Sex sex, String username, String password) {
            this.usertype = usertype;
            this.sid = sid;
            this.avatar = avatar;
            this.city = city;
            this.sex = sex;
            this.username = username;
            this.password = password;
        }
    }


    private class ViewHolder {
        private EditText username;
        private ImageView avatar;
        private TextView city;
        private RadioButton radio_male;
        private RadioButton radio_female;
        private RadioGroup group_sex;

        public ViewHolder(View view) {

            username = (EditText) view.findViewById(R.id.username);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            city = (TextView) view.findViewById(R.id.city);
            radio_male = (RadioButton) view.findViewById(R.id.radio_male);
            radio_male.setTag(Sex.Male);
            radio_female = (RadioButton) view.findViewById(R.id.radio_female);
            radio_female.setTag(Sex.Female);
            group_sex = (RadioGroup) view.findViewById(R.id.group_sex);

        }

        public void setCity(String city) {
            if (city == null) {
                this.city.setSelected(false);
                this.city.setText("未获取");
                this.city.setTag("未获取");
            } else {
                this.city.setTag(city);
                this.city.setSelected(true);
                this.city.setText(city);
            }
        }

        public void setSex(Sex sex) {
            radio_male.setChecked(false);
            radio_female.setChecked(false);
            switch (sex) {
                case Male:
                    radio_male.setChecked(true);
                    break;
                case Female:
                    radio_female.setChecked(true);
                    break;
            }
        }

        public void fill(Model model) {
            username.setText("" + model.username);
            setCity(model.city);
            /*if(model.sex != Sex.UnKouwn){
                setSex(model.sex);
            }*/


        }


    }

    private ViewHolder holder;

    private Model model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = (Model) getArguments().getSerializable("model");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filluser, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        holder = new ViewHolder(view);
        holder.radio_male.performClick();
        holder.fill(model);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.btn_avatar).setOnClickListener(this);
        view.findViewById(R.id.btn_city).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                onsubmit();
                break;
            case R.id.btn_cancel:
                getActivity().finish();
                break;
            case R.id.btn_avatar:
                ((BaseActivity) getActivity()).getPhoto(this);
                break;
            case R.id.btn_city:
                ((BaseActivity) getActivity()).getCity(this);
                break;
        }
    }

    private void onsubmit() {

        if (result == null) {
            Utils.tip(getActivity(), "请选择头像");
            return;
        }
        final String nickname = holder.username.getText().toString();
        if (TextUtils.isEmpty(nickname)) {
            Utils.tip(getActivity(), "请填写昵称");
            return;
        }
        LoadingPopup.show(getActivity());
        IsHaveUserNameRequest haveSingerName = new IsHaveUserNameRequest(getActivity(), new IsHaveUserNameRequest.Params(model.sid, model.usertype));
        haveSingerName.request(new BaseClient.RequestHandler() {

            @Override
            public void onSuccess(Object object) {
                String city = "";
                if (holder.city.getTag() != null) {
                    city = holder.city.getTag().toString();
                }
                Sex sex = (Sex) holder.group_sex.findViewById(holder.group_sex.getCheckedRadioButtonId()).getTag();



                SetClientUserRequest request = new SetClientUserRequest(getActivity(),new SetClientUserRequest.Params(nickname,model.sid,""+model.usertype,sex,result.getGuid(),result.getExt(),city,model.password));

                /*if (model.usertype  == UserType.USERTYPE_USERNAME) {
                    request = new SetSingerRequest(getActivity(),
                            new SetSingerRequest.Params(model.account.wbqqid, model.account.type, nickname, sex, result.getGuid(), result.getExt(), city));
                } else {
                    request = new SetSingerUserRequest(getActivity(),
                            new SetSingerUserRequest.Params(nickname, sex.getCode(), result.getGuid(), result.getExt(), city, model.phoneAccount.phone, model.phoneAccount.password));
                }*/

                request.request(new BaseClient.SimpleRequestHandler() {
                    @Override
                    public void onSuccess(Object object) {
                        Utils.tip(getActivity(), "注册成功");
                        SetClientUserRequest.Result result = (SetClientUserRequest.Result)object;
                        Preferences.getInstance().login(result.getUserid(),result.getPkey());
                        ZoneActivity.login(getActivity());
                    }

                    @Override
                    public void onComplete() {
                        LoadingPopup.hide(getActivity());
                    }

                    @Override
                    public void onError(int error, String msg) {
                        Utils.tip(getActivity(),"注册失败");
                    }
                });

            }

            @Override
            public void onError(int error, String msg) {
                if (error == 1006) {
                    Utils.tip(getActivity(), "昵称重复");
                }

            }

            @Override
            public void onComplete() {
                LoadingPopup.hide(getActivity());

            }
        });
    }

    @Override
    public void onGallerySelection(File file) {
        LoadingPopup.show(getActivity());
        AliyunUtils.getInstance().upload(CropImageUtils.cropImage(getActivity(), file, 300, 300), "cctv11cdn", this);
    }

    private AliyunUtils.UploadResult result;

    @Override
    public void onsuccess(AliyunUtils.UploadResult result) {

        this.result = result;
        LoadingPopup.hide(getActivity());
        ImageLoader.getInstance().displayImage(result.getUrl(), holder.avatar,
                DisplayOptions.IMG.getOptions());

    }

    @Override
    public void onCitySelection(String city) {
        holder.setCity(city);

    }

}
