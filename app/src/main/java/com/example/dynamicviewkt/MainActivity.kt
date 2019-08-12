package com.example.dynamicviewkt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.one_layout.view.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG: String? = MainActivity::class.simpleName
        private val REQ_CAMERA_IMG = 101
    }
    private var ticketTypeList = ArrayList<Ticket>()

    private var rotateDown: Animation? = null

    private var rotateUp: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        initAnimation()

        var t1 = Ticket(ticketHeading = "one", stringOption = arrayListOf<String>("one", "two", "three"))
        ticketTypeList.add(t1)
        var t2 = Ticket(ticketHeading = "two", stringOption = arrayListOf<String>("one", "two", "three"))
        ticketTypeList.add(t2)

        var t3 = Ticket(ticketHeading = "three", stringOption = arrayListOf<String>("one", "two", "three"))
        ticketTypeList.add(t3)

        var t4 = Ticket(ticketHeading = "four", stringOption = arrayListOf<String>("one", "two", "three"))
        ticketTypeList.add(t4)

        var t5 = Ticket(ticketHeading = "five", stringOption = arrayListOf<String>("one", "two", "three"))
        ticketTypeList.add(t5)

        for (ticket in ticketTypeList) {
            addLayout(ticket.ticketHeading, ticket.stringOption)
        }


    }

    private fun addLayout(heading: String, stringOption: List<String>) {

        var view = LayoutInflater.from(this)
            .inflate(R.layout.one_layout, parent_ll, false)

        view.heading_tv.text = heading


        for (s in stringOption) {
            var radioButton = RadioButton(this)
            radioButton.text = s
            view.option_rg.addView(radioButton)

        }

        view.heading_tv.setOnClickListener(clickListener)

        parent_ll.addView(view)

    }

    private fun initAnimation() {
        rotateDown = AnimationUtils.loadAnimation(this, R.anim.rotate_down)
        rotateUp = AnimationUtils.loadAnimation(this, R.anim.rotate_up)
    }


    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.heading_tv -> {

                val text = (view as TextView).text.toString()

                for (ticket in ticketTypeList) {
                    if (ticket.ticketHeading.equals(text)) {

                        val linearLayout =
                            parent_ll.getChildAt(ticketTypeList.indexOf(ticket))
                                .findViewById<LinearLayout>(R.id.child_layout_ll)
                        val ivDropDown =
                            parent_ll.getChildAt(ticketTypeList.indexOf(ticket))
                                .findViewById<ImageView>(R.id.iv_drop_down)



                        if (linearLayout.visibility == View.GONE) {

                            linearLayout.visible()
                            linearLayout.expand()

                            ivDropDown.startAnimation(rotateUp)
                        } else {
                            linearLayout.collapse()

                            ivDropDown.startAnimation(rotateDown)


                        }


                    } else {

                        val linearLayout =
                            parent_ll.getChildAt(ticketTypeList.indexOf(ticket))
                                .findViewById<LinearLayout>(R.id.child_layout_ll)
                        val ivDropDown =
                            parent_ll.getChildAt(ticketTypeList.indexOf(ticket))
                                .findViewById<ImageView>(R.id.iv_drop_down)



                        if (linearLayout.visibility == View.VISIBLE) {
                            linearLayout.collapse()
                            ivDropDown.startAnimation(rotateDown)
                        }

                    }
                }
            }
        }


    }

}
