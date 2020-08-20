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
    public partial class AdministratorForm : Common.UI.TemplateForm
    {
        BindingList<UserItem> userList = new BindingList<UserItem>();

        class UserItem
        {
            public User User { get; set; }
            public int Id { get; set; }
            public string FirstName { get; set; }
            public string LastName { get; set; }
            public DateTime Birthdate { get; set; }

            public int Age => Util.GetAge(Birthdate);
            public string Email { get; set; }
            public string Office { get; set; }
            public string Role { get; set; }
            public bool Active { get; set; }

            public UserItem(User user, int id, string firstName, string lastName, DateTime birthdate, string email, string office, string role, bool active)
            {
                User = user;
                Id = id;
                FirstName = firstName;
                LastName = lastName;
                Birthdate = birthdate;
                Email = email;
                Office = office;
                Role = role;
                Active = active;
            }
        }

        public AdministratorForm()
        {
            InitializeComponent();

            dgUser.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(UserItem.FirstName), HeaderText = "First Name" });
            dgUser.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(UserItem.LastName), HeaderText = "Last Name" });
            dgUser.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 0.3f, DataPropertyName = nameof(UserItem.Age), HeaderText = "Age",
                DefaultCellStyle = new DataGridViewCellStyle() { Alignment = DataGridViewContentAlignment.MiddleCenter }
            });
            dgUser.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(UserItem.Role), HeaderText = "User Role" });
            dgUser.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 2f, DataPropertyName = nameof(UserItem.Email), HeaderText = "Email Address" });
            dgUser.Columns.Add(new DataGridViewTextBoxColumn() { FillWeight = 1f, DataPropertyName = nameof(UserItem.Office), HeaderText = "Office" });

            dgUser.DataSource = userList;

            cboOffice.Items.Add(new ComboItem(0, "All Offices"));

            foreach (var item in context.Offices)
            {
                cboOffice.Items.Add(new ComboItem(item.ID, item.Title));
            }

            cboOffice.SelectedIndex = 0;
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

        /// <summary>
        /// Add User Click Button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AddUserToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new AddUserForm().ShowDialog();
            CboOffice_SelectedIndexChanged(null, new EventArgs());
        }

        /// <summary>
        /// Office ComboBox Change Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CboOffice_SelectedIndexChanged(object sender, EventArgs e)
        {
            userList.Clear();

            IQueryable<User> userContext = context.Users;

            if (cboOffice.SelectedIndex > 0)
            {
                var office = cboOffice.SelectedItem as ComboItem;

                userContext = userContext.Where(x => x.OfficeID == office.Id);
            }

            foreach (var user in userContext)
            {
                userList.Add(new UserItem(user, user.ID, user.FirstName, user.LastName, user.Birthdate.Value, user.Email, user.Office.Title, user.Role.Title, user.Active.Value));
            }

        }


        private void DgvUser_CellPainting(object sender, DataGridViewCellPaintingEventArgs e)
        {
            if (e.RowIndex == -1)
            {
                return;
            }

            UserItem user = userList[e.RowIndex];

            if (user.Role[0] == 'A')
            {
                e.CellStyle.BackColor = Res.LightBlue;
            }
            else if (user.Active == false)
            {
                e.CellStyle.BackColor = Res.MainOrange;
            }
        }

        /// <summary>
        /// Active Toggle Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnActiveToggle_Click(object sender, EventArgs e)
        {
            if (dgUser.GetSeltectedRow != -1)
            {
                UserItem userItem = userList[dgUser.GetSeltectedRow];
                User user = userItem.User;
                
                if (userItem.Role[0] == 'A')
                {
                    MessageError("The selected user is administrator!\nPlease select a user is not administrator!");
                    return;
                }

                if (userItem.Active)
                {
                    MessageInfo($"The '{userItem.Email}' account is disabled.");
                } else
                {
                    MessageInfo($"The '{userItem.Email}' account is enabled.");
                }

                user.Active = !user.Active.Value;
                userItem.Active = user.Active.Value;

                context.SaveChanges();
            }
        }

        /// <summary>
        /// Change Role Button Click Event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void BtnChange_Click(object sender, EventArgs e)
        {
            if (dgUser.GetSeltectedRow != -1)
            {
                UserItem userItem = userList[dgUser.SelectedRows[0].Index];

                new EditRoleForm(userItem.User).ShowDialog();

                userItem.Role = userItem.User.Role.Title;
            }
        }
    }
}
