package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.Intent
import android.content.Intent.*
import android.content.SharedPreferences
import android.net.Uri
import android.net.Uri.fromParts
import android.net.Uri.parse
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.Gravity.LEFT
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window.FEATURE_NO_TITLE
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import fpt.adtrue.horoscope.BaseActivity
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.HomePagerAdapter
import fpt.adtrue.horoscope.api.Utils.fadeVisibility
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.application.App.Companion.SETTING
import fpt.adtrue.horoscope.application.App.Companion.SIGN
import fpt.adtrue.horoscope.application.App.Companion.getViewModel
import fpt.adtrue.horoscope.application.App.Companion.getZodiac
import fpt.adtrue.horoscope.broadcast.NotificationActionService
import fpt.adtrue.horoscope.databinding.ActivityMainBinding
import java.lang.String.format
import kotlin.system.exitProcess

@Suppress("DEPRECATION")
class MainActivity : BaseActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("RtlHardcoded", "NewApi", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.logoSign.setImageResource(getZodiac()[SIGN].image)
        binding.slidingTabs.setOnTabSelectedListener(this)

        binding.tvNameSign.text = getZodiac()[SIGN].name

// ________________________________________________________________________________________

//
//        getViewModel().data.observe(this, Observer {
//            binding.tvDate.text = it.currentDate
//        })
// ________________________________________________________________________________________

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        sttBar(this)
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(LEFT)
        }

        binding.data = getViewModel()
        binding.versionName.text =
            "Version Name: ${packageManager.getPackageInfo(packageName, 0).versionName}"

        binding.astroProfileBack.setOnClickListener {
            binding.about.fadeVisibility(GONE, 1000)
        }
        binding.about.setOnClickListener {}
        binding.toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_three_dot)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu2 -> {
                    binding.about.fadeVisibility(VISIBLE)
                    true
                }

                R.id.setting -> {
                    showDialog()
                    true
                }
                else -> false
            }
        }
        binding.navView.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.person_horo -> {
                    val intent = Intent(this, ProfileAstroActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.my_as_profile -> {
                    val intent = Intent(this, ChoiceCompatActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.tarot -> {
                    StartReadingTarotActivity.start(this)
                    true
                }

                R.id.contact_us -> {
                    val intent = Intent(
                        ACTION_SENDTO,
                        fromParts("mailto", "walkinsvicky@gmail.com", null)
                    )
                    intent.putExtra(EXTRA_SUBJECT, "subject")
                    intent.putExtra(EXTRA_TEXT, "Horoscope")
                    startActivity(createChooser(intent, "Choose apps to connect with us :"))
                    true
                }

                R.id.rate_us -> {
                    rateApp()
                    true
                }

                R.id.about_us -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    binding.about.fadeVisibility(VISIBLE)
                    true
                }

                else -> false
            }
        }

//        val runTab = Runnable {
//            if (binding.slidingTabs.width < this.resources.displayMetrics.widthPixels) {
//                binding.slidingTabs.tabMode = TabLayout.MODE_FIXED
//                val mParams: ViewGroup.LayoutParams = binding.slidingTabs.layoutParams
//                mParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//                binding.slidingTabs.layoutParams = mParams
//            } else {
//                binding.slidingTabs.tabMode = TabLayout.MODE_SCROLLABLE
//            }
//        }
//
//        binding.slidingTabs.post(runTab)
        binding.slidingTabs.setupWithViewPager(binding.viewpager)
        binding.viewpager.adapter = HomePagerAdapter(supportFragmentManager)
        binding.slidingTabs.getTabAt(1)?.select()
//        binding.viewpager.offscreenPageLimit = 2
        binding.title.setOnClickListener {
            val intent = Intent(applicationContext, ChooseSignActivity::class.java)
            startActivities(arrayOf(intent))
            binding.title.isEnabled = false
            val enableButton = Runnable { binding.title.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }
    }

    private fun rateApp() {
        try {
            val rateIntent = rateIntentForUrl("market://details")
            startActivity(rateIntent)
        } catch (e: ActivityNotFoundException) {
            val rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details")
            startActivity(rateIntent)
        }
    }

    private fun rateIntentForUrl(url: String): Intent {
        val intent =
            Intent(ACTION_VIEW, parse(format("%s?id=%s", url, applicationContext.packageName)))
        var flags = FLAG_ACTIVITY_NO_HISTORY or FLAG_ACTIVITY_MULTIPLE_TASK
        flags = if (Build.VERSION.SDK_INT >= 21) {
            flags or FLAG_ACTIVITY_NEW_DOCUMENT
        } else {
            flags or FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
        }
        intent.addFlags(flags)
        return intent
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> {
                getViewModel().data2.observe(this, Observer {
                    binding.tvDate.text = it.currentDate
                })
            }
            1 -> {
                getViewModel().data.observe(this, Observer {
                    binding.tvDate.text = it.currentDate
                })
            }
            else -> {
                getViewModel().data1.observe(this, Observer {
                    binding.tvDate.text = it.currentDate
                })
            }

        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_setting)
        val yesBtn = dialog.findViewById(R.id.iv_close) as ImageView
        val noBtn = dialog.findViewById(R.id.switch1) as Switch
        yesBtn.setOnClickListener { dialog.dismiss() }
        noBtn.isChecked = SETTING
        noBtn.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    SETTING = true
                    setDataLocal(SETTING)
                    val intent =
                        Intent(applicationContext, NotificationActionService::class.java).setAction(
                            "STOP"
                        )
                    applicationContext.sendBroadcast(intent)
                }
                false -> {
                    SETTING = false
                    setDataLocal(SETTING)
                    val intent =
                        Intent(applicationContext, NotificationActionService::class.java).setAction(
                            "PLAY"
                        )
                    applicationContext.sendBroadcast(intent)
                }
            }
        }
        dialog.show()
    }

    private fun setDataLocal(setting: Boolean) {
        val sharedPreferences: SharedPreferences =
            applicationContext.getSharedPreferences("setting", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("SETTING", setting)
        editor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        makeText(applicationContext, "Font Size", LENGTH_SHORT).show()

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(R.string.do_you_really_want_to_exit)
        alertDialog.setButton(BUTTON_POSITIVE, "Yes") { _, _ ->
            finishAffinity()
            exitProcess(0)
        }
        alertDialog.setButton(BUTTON_NEGATIVE, "No") { dialog, _ -> dialog!!.dismiss() }
        alertDialog.show()
    }
}