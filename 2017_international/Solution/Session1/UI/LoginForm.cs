using Common;
using Common.DB;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Session1.UI
{
    public partial class LoginForm : Common.UI.TemplateForm
    {
        int failCount = 0;
        DateTime blockTime = DateTime.Now;

        public LoginForm()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Close Button Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button2_Click(object sender, EventArgs e)
        {
            Close();
        }

        /// <summary>
        /// Login Button Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button1_Click(object sender, EventArgs e)
        {
            if (DateTime.Now <= blockTime)
            {
                MessageError($"You have blocked login system!\nWait {(blockTime - DateTime.Now).TotalSeconds:0} seconds...");
                return;
            }

            var user = context.Users.FirstOrDefault(x => x.Email == txtEmail.Text);
            
            if (user == null || user.Password != Util.GetMD5String(txtPassword.Text))
            {
                // Login failed
                failCount++;
                
                if (failCount == 3)
                {
                    failCount = 0;
                    blockTime = DateTime.Now.AddSeconds(10);
                    MessageError($"You have blocked login system!\nWait 10 seconds...");
                } else
                {
                    MessageError($"You have entered wrong email or password!\nIf you have entered wrong information three times, you are blocking login system during 10 seconds.\n(Current fail count : {failCount})");
                }

            } else if (user.RoleID == 2 && user.Active == false)
            {
                // User disabled
                MessageError($"This account is disabled!\nContact to the administrator");
            } else
            {

                // Login sucessed
                Hide();

                if (user.RoleID == 1)
                {
                    // Administrator
                    new AdministratorForm().ShowDialog();
                } else
                {
                    // Just User
                    SessionManager.Session = user;
                    var lastLogin = context.UserLogs.Where(x => x.UserId == user.ID).OrderByDescending(x => x.Id).FirstOrDefault();

                    if (lastLogin != null && lastLogin.LogoutTime == null && lastLogin.IsConfirm == false)
                    {
                        // Detect wrong log
                        new NoLogoutDetectForm(lastLogin).ShowDialog();
                    }

                    var log = new UserLog() {
                        LoginTime = DateTime.Now,
                        UserId = user.ID,
                        IsConfirm = false
                    };

                    context.UserLogs.Add(log);
                    context.SaveChanges();

                    SessionManager.SessionLog = log;

                    new MainMenuForm(user).ShowDialog();
                }

                Close();
            }
        }
    }
}
