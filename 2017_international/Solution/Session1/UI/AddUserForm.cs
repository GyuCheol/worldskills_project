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
    public partial class AddUserForm : Common.UI.TemplateForm
    {
        
        public AddUserForm()
        {
            InitializeComponent();

            foreach (var item in context.Offices)
            {
                cboOffice.Items.Add(new ComboItem(item.ID, item.Title));
            }
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

        /// <summary>
        /// Save Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnSave_Click(object sender, EventArgs e)
        {
            if (txtEmail.Text == "" || txtFirstName.Text == "" || txtFirstName.Text == "" || cboOffice.SelectedIndex == -1 || txtPassword.Text == "")
            {
                MessageError("All fields need to be filled!");
                return;
            }

            var user = context.Users.OrderByDescending(x => x.ID).FirstOrDefault();
            int id = user == null ? 1 : user.ID + 1;

            if (context.Users.FirstOrDefault(x => x.Email == txtEmail.Text) != null)
            {
                MessageError($"The entered email is already created!");
                return;
            }

            context.Users.Add(new User() {
                ID = id,
                Email = txtEmail.Text,
                FirstName = txtFirstName.Text,
                LastName = txtLastName.Text,
                OfficeID = (cboOffice.SelectedItem as ComboItem).Id,
                Birthdate = dtpBirthdate.Value,
                Password = Util.GetMD5String(txtPassword.Text),
                RoleID = 2,
                Active = true
            });
            context.SaveChanges();

            MessageInfo("Welcome!");

            Close();
        }
    }
}
