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

namespace Session2.UI
{
    public partial class FlightScheduleForm : Common.UI.TemplateForm
    {
        BindingList<ScheduleItem> scheduleList = new BindingList<ScheduleItem>();

        class ScheduleItem
        {
            public Schedule Schedule { get; set; }
            public string Date { get; set; }
            public string Time { get; set; }
            public string From { get; set; }
            public string To { get; set; }
            public string Flight { get; set; }
            public string Aircraft { get; set; }
            public string Economy { get; set; }
            public string Business { get; set; }
            public string First { get; set; }

            public ScheduleItem(Schedule schedule, string date, string time, string from, string to, string flight, string aircraft, string economy, string business, string first)
            {
                Schedule = schedule;
                Date = date;
                Time = time;
                From = from;
                To = to;
                Flight = flight;
                Aircraft = aircraft;
                Economy = economy;
                Business = business;
                First = first;
            }
        }

        public FlightScheduleForm()
        {
            InitializeComponent();

            foreach (var airport in context_s2.Airports)
            {
                var comboItem = new ComboItem(airport.ID, airport.Name);

                cboFrom.Items.Add(comboItem);
                cboTo.Items.Add(comboItem);
            }

            cboSort.Items.Add("Date-Time");
            cboSort.Items.Add("Price");
            cboSort.SelectedIndex = 0;
            cboFrom.SelectedIndex = 0;
            cboTo.SelectedIndex = 1;

            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(ScheduleItem.Date), HeaderText = "Date" });
            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 0.6f, DataPropertyName = nameof(ScheduleItem.Time), HeaderText = "Time" });
            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 0.6f, DataPropertyName = nameof(ScheduleItem.From), HeaderText = "From" });
            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 0.6f, DataPropertyName = nameof(ScheduleItem.To), HeaderText = "To" });
            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(ScheduleItem.Flight), HeaderText = "Flight number" });
            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(ScheduleItem.Aircraft), HeaderText = "Aircraft" });
            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(ScheduleItem.Economy), HeaderText = "Economy price" });
            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(ScheduleItem.Business), HeaderText = "Business price" });
            dgSchedule.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(ScheduleItem.First), HeaderText = "First class price" });

            dgSchedule.DataSource = scheduleList;

            dtpOutbound.Value = new DateTime(2017, 10, 1);

            BindFields(chkFrom, cboFrom);
            BindFields(chkTo, cboTo);
            BindFields(chkOutbound, dtpOutbound);
            BindFields(chkFlight, txtFlight);
        }

        private void BindFields(CheckBox chk, Control control)
        {
            chk.CheckedChanged += (s, e) => control.Enabled = chk.Checked;
        }

        /// <summary>
        /// Apply Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnApply_Click(object sender, EventArgs e)
        {
            IQueryable<Schedule> query = context_s2.Schedules;

            if (chkFrom.Checked && chkTo.Checked && cboFrom.SelectedIndex == cboTo.SelectedIndex)
            {
                MessageError("The departure and arrival cannot be the same!");
                return;
            }

            if (chkFrom.Checked)
            {
                ComboItem fromItem = cboFrom.SelectedItem as ComboItem;

                query = query.Where(x => x.Route.DepartureAirportID == fromItem.Id);
            }

            if (chkTo.Checked)
            {
                ComboItem toItem = cboTo.SelectedItem as ComboItem;

                query = query.Where(x => x.Route.ArrivalAirportID == toItem.Id);
            }

            if (chkOutbound.Checked)
            {
                query = query.Where(x => x.Date == dtpOutbound.Value);
            }

            if (chkFlight.Checked)
            {
                query = query.Where(x => x.FlightNumber.Contains(txtFlight.Text));
            }

            if (cboSort.SelectedIndex == 0)
            {
                query = query.OrderBy(x => x.Date).ThenBy(x => x.Time);
            } else
            {
                query = query.OrderBy(x => x.EconomyPrice);
            }

            scheduleList.Clear();

            foreach (Schedule schedule in query)
            {
                decimal business = schedule.EconomyPrice * 1.35m;
                decimal first = business * 1.3m;

                scheduleList.Add(new ScheduleItem(schedule, 
                    $"{schedule.Date:dd\\/MM\\/yyyy}",
                    $"{schedule.Time:hh\\:mm}",
                    schedule.Route.Airport.IATACode,
                    schedule.Route.Airport1.IATACode,
                    schedule.FlightNumber,
                    schedule.Aircraft.Name,
                    $"{schedule.EconomyPrice:$#,##0}",
                    $"{business:$#,##0}",
                    $"{first:$#,##0}"
                ));
            }
        }

        /// <summary>
        /// Cancel Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnCancel_Click(object sender, EventArgs e)
        {
            int row = dgSchedule.GetSeltectedRow;

            if (row != -1)
            {
                var schedule = scheduleList[row].Schedule;

                schedule.Confirmed = !schedule.Confirmed;
                SwitchConfirmButton(schedule.Confirmed);

                context_s2.SaveChanges();
            }
        }

        /// <summary>
        /// Edit Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnEdit_Click(object sender, EventArgs e)
        {
            int row = dgSchedule.GetSeltectedRow;

            if (row != -1)
            {
                new ScheduleEditForm(scheduleList[row].Schedule).ShowDialog();
                BtnApply_Click(null, null);
            }
        }

        /// <summary>
        /// Import Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnImport_Click(object sender, EventArgs e)
        {
            new ImportForm().ShowDialog();
        }

        private void SwitchConfirmButton(bool isConfirmed)
        {
            if (isConfirmed)
            {
                btnCancel.ImageIndex = 0;
                btnCancel.Text = "Cancel Flight";
                btnCancel.BackColor = Res.LightGold;
                btnCancel.ForeColor = Color.Red;
            } else
            {
                btnCancel.ImageIndex = 1;
                btnCancel.Text = "Confirm Flight";
                btnCancel.BackColor = Res.MainBlue;
                btnCancel.ForeColor = Res.LightGold;
            }
        }

        /// <summary>
        /// Item Selection Change Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DgSchedule_SelectionChanged(object sender, EventArgs e)
        {
            int row = dgSchedule.GetSeltectedRow;

            if (row != -1)
            {
                var schedule = scheduleList[row].Schedule;

                SwitchConfirmButton(schedule.Confirmed);
            }
        }

        private void DgSchedule_CellPainting(object sender, DataGridViewCellPaintingEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                var schedule = scheduleList[e.RowIndex].Schedule;

                if (schedule.Confirmed == false)
                {
                    e.CellStyle.BackColor = Res.MainGold;
                }

            }
        }
    }
}
