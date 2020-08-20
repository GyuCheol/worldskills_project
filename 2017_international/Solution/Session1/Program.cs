using Common.DB;
using Session1.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Session1
{
    static class Program
    {
        
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.ApplicationExit += Application_ApplicationExit;

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginForm());
            // Application.Run(new AdministratorForm());
        }

        private static void Application_ApplicationExit(object sender, EventArgs e)
        {
            if (SessionManager.SessionLog != null)
            {
                SessionManager.SessionLog.LogoutTime = DateTime.Now;
                SessionManager.context.SaveChanges();
            }
        }
    }
}
