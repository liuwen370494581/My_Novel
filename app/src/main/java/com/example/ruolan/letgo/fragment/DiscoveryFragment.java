package com.example.ruolan.letgo.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Dialog.TipandEditDialog;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.LoadLocalFileAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.PermissionCheck;
import com.example.ruolan.letgo.Utils.ToastUtils;

/**
 * Created by ruolan on 2015/11/29.
 * 发现
 */
public class DiscoveryFragment extends BaseFragment {
    private Button btnSmartScan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view) {
        btnSmartScan = (Button) view.findViewById(R.id.btn_scan);

    }

    private void setListener() {
        btnSmartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检查权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !PermissionCheck.checkPremission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x11);
                } else {
                    LoadData();
                    btnSmartScan.setVisibility(View.GONE);
                }


            }
        });
    }


    private void LoadData() {
        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        LoadLocalFileAction.searchLocalFile(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                hideLoadingDialog();
                Log.e(Config.TAG, object.toString());
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });
    }

    @Override
    public void initData() {

    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0x11) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && PermissionCheck.checkPremission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                LoadData();
                btnSmartScan.setVisibility(View.GONE);
            } else {
                if (!getActivity().shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    final TipandEditDialog dialog = new TipandEditDialog(getActivity(), "去系统设置打开SD卡读写权限?");
                    dialog.show();
                    dialog.setLeftText("取消");
                    // dialog.setLeftTextColor(getResources().getColor(R.color.black));
                    dialog.setRightText("确定");
                    dialog.setListener(new TipandEditDialog.ITipEndEditDialogListener() {
                        @Override
                        public void ClickLeft() {
                            dialog.dismiss();
                        }

                        @Override
                        public void ClickRight() {
                            PermissionCheck.requestPermissionSetting(getActivity());
                        }
                    });

                } else {
                    ToastUtils.showToast(getActivity(), "未获取SD卡读取权限");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
