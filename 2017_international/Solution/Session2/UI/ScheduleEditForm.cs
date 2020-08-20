using Common.DB;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Session2.UI
{
    public partial class ScheduleEditForm : Common.UI.TemplateForm
    {
        public ScheduleEditForm()
        {
            InitializeComponent();
        }

        public ScheduleEditForm(Schedule schedule)
            : this()
        {
            lbFrom.Text = schedule.Route.Airport.IATACode;
            lbTo.Text = schedule.Route.Airport1.IATACode;
            lbAircraft.Text = schedule.Aircraft.Name;

            dtpDate.Value = schedule.Date;
            dtpTime.Value = DateTime.Today + schedule.Time;
            nudPrice.Value = schedule.EconomyPrice;

            btnApply.Click += (s, e) => {
                schedule.Date = dtpDate.Value;
                schedule.Time = dtpTime.Value.TimeOfDay;
                schedule.EconomyPrice = nudPrice.Value;

                context_s2.SaveChanges();

                MessageInfo("The schedule is successfully edited!");

                Close();
            };

        }

        /// <summary>
        /// Cancel Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnCancel_Click(object sender, EventArgs e)
        {
            Close();
        }

    }
}
