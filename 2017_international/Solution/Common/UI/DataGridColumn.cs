using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Common.UI
{
    public class DataGridColumn : DataGridViewTextBoxColumn
    {

        public DataGridColumn(float w, string property, string header)
        {
            FillWeight = w;
            DataPropertyName = property;
            HeaderText = header;

        }

    }
}
