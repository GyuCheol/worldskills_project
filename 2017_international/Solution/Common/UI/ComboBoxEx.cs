using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Common.UI
{
    public class ComboBoxEx : ComboBox
    {

        public ComboBoxEx()
        {
            DropDownStyle = ComboBoxStyle.DropDownList;
            FlatStyle = FlatStyle.Popup;
        }

    }
}
