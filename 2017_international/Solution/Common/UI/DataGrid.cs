using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Common.UI
{
    public class DataGrid : DataGridView
    {

        public DataGrid()
        {
            AutoGenerateColumns = false;
            AllowUserToAddRows = false;
            AllowUserToDeleteRows = false;
            AllowUserToOrderColumns = false;
            AllowUserToResizeColumns = false;
            AllowUserToResizeRows = false;

            MultiSelect = false;
            RowHeadersVisible = false;

            SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            EditMode = DataGridViewEditMode.EditProgrammatically;
            AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;

            BackgroundColor = Color.White;
        }

        public int GetSeltectedRow => SelectedRows.Count == 0 ? -1 : SelectedRows[0].Index;

    }
}
