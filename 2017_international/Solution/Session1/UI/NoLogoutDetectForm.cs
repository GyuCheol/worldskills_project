using Common.DB;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Session1.UI
{
    public partial class NoLogoutDetectForm : Common.UI.TemplateForm
    {
        public NoLogoutDetectForm()
        {
            InitializeComponent();
        }

        public NoLogoutDetectForm(UserLog log)
            : this()
        {
            lbTime.Text = $"No logout detected for your last login on {log.LoginTime:MM/dd/yyyy} at {log.LoginTime:HH:mm}";

            // Confirm Event
            btnConfirm.Click += (s, e) =>
            {
                log.Reason = txtReason.Text;
                log.ReasonType = rdoSoftware.Checked ? "Software" : "System";
                log.IsConfirm = true;

                SessionManager.context.SaveChanges();
                Close();
            };
        }

    }
}
