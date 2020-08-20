using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Common.UI
{
    public class DatePickerEx : DateTimePicker
    {

        public DatePickerEx()
        {
            CustomFormat = "dd/MM/yyyy";
            Format = DateTimePickerFormat.Custom;
        }
    }
}
