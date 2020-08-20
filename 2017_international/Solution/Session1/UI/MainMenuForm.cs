using Common.DB;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Common;

namespace Session1.UI
{
    public partial class MainMenuForm : Common.UI.TemplateForm
    {
        BindingList<LogItem> logList = new BindingList<LogItem>();

        class LogItem
        {
            public string Date { get; set; }
            public string LoginTime { get; set; }
            public string LogoutTime { get; set; }
            public string TimeSpent { get; set; }
            public string Reason { get; set; }

            public LogItem(string date, string loginTime, string logoutTime, string timeSpent, string reason)
            {
                Date = date;
                LoginTime = loginTime;
                LogoutTime = logoutTime;
                TimeSpent = timeSpent;
                Reason = reason;
            }
        }

        public MainMenuForm()
        {
            InitializeComponent();

            dgLog.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(LogItem.Date), HeaderText = "Date" });
            dgLog.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(LogItem.LoginTime), HeaderText = "Login time" });
            dgLog.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(LogItem.LogoutTime), HeaderText = "Logout time" });
            dgLog.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 2f, DataPropertyName = nameof(LogItem.TimeSpent), HeaderText = "Time spent on system" });
            dgLog.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 2.5f, DataPropertyName = nameof(LogItem.Reason), HeaderText = "Unsuccessful logout reason" });

            dgLog.DataSource = logList;
        }

        public MainMenuForm(User user)
            : this()
        {
            lbWelcome.Text = $"Hi {user.FirstName} {user.LastName}, Welcome to AMONIC Airlines.";

            DateTime before30days = DateTime.Today.AddDays(-30);
            var logs = from n in context.UserLogs
                      where n.UserId == user.ID && n.LoginTime >= before30days
                      orderby n.Id descending
                      select n;

            int crash = 0;
            TimeSpan totalSpent = new TimeSpan();

            foreach (var log in logs)
            {
                if (log.Id == SessionManager.SessionLog.Id)
                {
                    continue;
                }

                if (log.LogoutTime == null)
                {
                    crash++;
                    logList.Add(new LogItem($"{log.LoginTime:MM\\/dd\\/yyyy}", $"{log.LoginTime:HH:mm}", "**", "**", log.Reason));
                } else
                {
                    TimeSpan spent = log.LogoutTime.Value - log.LoginTime;

                    logList.Add(new LogItem($"{log.LoginTime:MM\\/dd\\/yyyy}", $"{log.LoginTime:HH:mm}", $"{log.LogoutTime.Value:HH:mm}", $"{spent.TotalHours:0}:{spent:mm\\:ss}", ""));
                    totalSpent += spent;
                }
            }

            lbCrashes.Text = $"Number of crashes : {crash}";
            lbSpent.Text = $"Time spent on system : {totalSpent.TotalHours:0}:{totalSpent:mm\\:ss}";
        }

        /// <summary>
        /// Exit Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ExitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void DgLog_CellPainting(object sender, DataGridViewCellPaintingEventArgs e)
        {
            int row = e.RowIndex;

            if (row != -1)
            {
                LogItem log = logList[row];

                if (log.LogoutTime == "**")
                {
                    e.CellStyle.BackColor = Res.MainOrange;
                }

            }
        }
    }
}
