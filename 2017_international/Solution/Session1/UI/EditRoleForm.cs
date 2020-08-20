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
    public partial class EditRoleForm : Common.UI.TemplateForm
    {
        public EditRoleForm()
        {
            InitializeComponent();

            foreach (var item in context.Offices)
            {
                cboOffice.Items.Add(item.Title);
            }

        }

        public EditRoleForm(User user)
            : this()
        {
            txtEmail.Text = user.Email;
            txtFirstName.Text = user.FirstName;
            txtLastName.Text = user.LastName;
            cboOffice.Text = user.Office.Title;

            if (user.RoleID == 1)
            {
                rdoAdmin.Checked = true;
            } else
            {
                rdoUser.Checked = true;
            }

            // Apply Button Click Event
            btnApply.Click += (s, e) =>
            {
                user.RoleID = rdoAdmin.Checked ? 1 : 2;
                context.SaveChanges();

                MessageInfo("Role have successfully edited.");
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
