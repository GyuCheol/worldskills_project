using Common.DB;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Common.UI
{
    public partial class TemplateForm : Form
    {
        public static Session1Db context_s1 = new Session1Db();
        public static session2Entities context_s2 = new session2Entities();

        public TemplateForm()
        {
            Font = Res.PrimaryFont;

            InitializeComponent();
        }

        public static void MessageError(string text)
        {
            MessageBox.Show(text, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }

        public static void MessageInfo(string text)
        {
            MessageBox.Show(text, "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }
    }
}
