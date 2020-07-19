package ir.apptune.antispam.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.utils.CalendarTool
import ir.apptune.antispam.Checkcontact
import ir.apptune.antispam.ExternalDbOpenHelper
import ir.apptune.antispam.R
import ir.apptune.antispam.adapters.CallHistoryAdapter.MyViewHolder
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.utils.DATABASE_NAME
import kotlinx.android.synthetic.main.call_history_item.view.*
import java.util.*

class CallHistoryAdapter(private var list: ArrayList<CallModel>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.call_history_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: CallModel) {
            var number: String
            itemView.txtName.text = model.phNumber
            number = model.phNumber
            if (number.substring(0, 1)[0] == '*') {
                return
            }
            if (number.substring(0, 1)[0] == '+' || number.substring(0, 1)[0] == '0') {
                if (number.substring(0, 1)[0] == '0') {
                    number = "+98" + number.substring(1)
                }
                if (number.length == 13) {
                    number = number.replace("+98", "")
                    var name: CharSequence? = null
                    val database = ExternalDbOpenHelper(itemView.context, DATABASE_NAME).openDataBase()
                    for (i in 2..8) {
                        val friendCursor = database.rawQuery("SELECT * from phone_location where _id=" + number.substring(0, i), null)
                        friendCursor.moveToFirst()
                        if (friendCursor.count != 0) {
                            name = friendCursor.getString(1)
                            itemView.txtCity.text = name
                        }
                        friendCursor.close()
                    }
                    if (TextUtils.isEmpty(name)) {
                        itemView.txtCity.text = "شماره تلفن پیدا نشد!"
                    }
                }
            }
            val calendar: Calendar = GregorianCalendar()
            calendar.time = model.callDayTime
            val tool = CalendarTool(calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
            itemView.txtDate.text = tool.iranianDate
            val dir = model.dir
            if (dir != null) {
                when (dir) {
                    "OUTGOING" -> {
                        itemView.imgCallStatus.setImageResource(R.drawable.phone_call_out_going)
                    }
                    "INCOMING" -> {
                        itemView.imgCallStatus.setImageResource(R.drawable.ic_incomming_call)
                    }
                    "MISSED" -> {
                        itemView.imgCallStatus.setImageResource(R.drawable.ic_missed_call)
                    }
                }
            }
            var contactName: String? = null
            try {
                contactName = Checkcontact.getContactName(itemView.context, model.phNumber)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (contactName != null && contactName != "") {
                itemView.txtName.text = contactName
            } else {
                itemView.txtName.text = "شماره ناشناس"
            }
        }
    }
}