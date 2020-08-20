namespace Session2.UI
{
    partial class FlightScheduleForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FlightScheduleForm));
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.chkFlight = new System.Windows.Forms.CheckBox();
            this.chkOutbound = new System.Windows.Forms.CheckBox();
            this.chkTo = new System.Windows.Forms.CheckBox();
            this.chkFrom = new System.Windows.Forms.CheckBox();
            this.txtFlight = new System.Windows.Forms.TextBox();
            this.dtpOutbound = new Common.UI.DatePickerEx();
            this.btnApply = new System.Windows.Forms.Button();
            this.cboSort = new Common.UI.ComboBoxEx();
            this.label3 = new System.Windows.Forms.Label();
            this.cboTo = new Common.UI.ComboBoxEx();
            this.cboFrom = new Common.UI.ComboBoxEx();
            this.dgSchedule = new Common.UI.DataGrid();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.btnCancel = new System.Windows.Forms.Button();
            this.btnImport = new System.Windows.Forms.Button();
            this.btnEdit = new System.Windows.Forms.Button();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgSchedule)).BeginInit();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.chkFlight);
            this.groupBox1.Controls.Add(this.chkOutbound);
            this.groupBox1.Controls.Add(this.chkTo);
            this.groupBox1.Controls.Add(this.chkFrom);
            this.groupBox1.Controls.Add(this.txtFlight);
            this.groupBox1.Controls.Add(this.dtpOutbound);
            this.groupBox1.Controls.Add(this.btnApply);
            this.groupBox1.Controls.Add(this.cboSort);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.cboTo);
            this.groupBox1.Controls.Add(this.cboFrom);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(857, 136);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Filter by";
            // 
            // chkFlight
            // 
            this.chkFlight.AutoSize = true;
            this.chkFlight.Checked = true;
            this.chkFlight.CheckState = System.Windows.Forms.CheckState.Checked;
            this.chkFlight.Location = new System.Drawing.Point(294, 99);
            this.chkFlight.Name = "chkFlight";
            this.chkFlight.Size = new System.Drawing.Size(148, 27);
            this.chkFlight.TabIndex = 5;
            this.chkFlight.Text = "Flight Number : ";
            this.chkFlight.UseVisualStyleBackColor = true;
            // 
            // chkOutbound
            // 
            this.chkOutbound.AutoSize = true;
            this.chkOutbound.Checked = true;
            this.chkOutbound.CheckState = System.Windows.Forms.CheckState.Checked;
            this.chkOutbound.Location = new System.Drawing.Point(22, 99);
            this.chkOutbound.Name = "chkOutbound";
            this.chkOutbound.Size = new System.Drawing.Size(122, 27);
            this.chkOutbound.TabIndex = 5;
            this.chkOutbound.Text = "Outbound : ";
            this.chkOutbound.UseVisualStyleBackColor = true;
            // 
            // chkTo
            // 
            this.chkTo.AutoSize = true;
            this.chkTo.Checked = true;
            this.chkTo.CheckState = System.Windows.Forms.CheckState.Checked;
            this.chkTo.Location = new System.Drawing.Point(294, 45);
            this.chkTo.Name = "chkTo";
            this.chkTo.Size = new System.Drawing.Size(57, 27);
            this.chkTo.TabIndex = 5;
            this.chkTo.Text = "To : ";
            this.chkTo.UseVisualStyleBackColor = true;
            // 
            // chkFrom
            // 
            this.chkFrom.AutoSize = true;
            this.chkFrom.Checked = true;
            this.chkFrom.CheckState = System.Windows.Forms.CheckState.Checked;
            this.chkFrom.Location = new System.Drawing.Point(22, 45);
            this.chkFrom.Name = "chkFrom";
            this.chkFrom.Size = new System.Drawing.Size(79, 27);
            this.chkFrom.TabIndex = 5;
            this.chkFrom.Text = "From : ";
            this.chkFrom.UseVisualStyleBackColor = true;
            // 
            // txtFlight
            // 
            this.txtFlight.Location = new System.Drawing.Point(462, 94);
            this.txtFlight.Name = "txtFlight";
            this.txtFlight.Size = new System.Drawing.Size(125, 31);
            this.txtFlight.TabIndex = 4;
            // 
            // dtpOutbound
            // 
            this.dtpOutbound.CustomFormat = "dd/MM/yyyy";
            this.dtpOutbound.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dtpOutbound.Location = new System.Drawing.Point(153, 94);
            this.dtpOutbound.Name = "dtpOutbound";
            this.dtpOutbound.Size = new System.Drawing.Size(125, 31);
            this.dtpOutbound.TabIndex = 3;
            // 
            // btnApply
            // 
            this.btnApply.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(25)))), ((int)(((byte)(106)))), ((int)(((byte)(166)))));
            this.btnApply.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(250)))), ((int)(((byte)(203)))));
            this.btnApply.Location = new System.Drawing.Point(720, 93);
            this.btnApply.Name = "btnApply";
            this.btnApply.Size = new System.Drawing.Size(131, 37);
            this.btnApply.TabIndex = 2;
            this.btnApply.Text = "&Apply";
            this.btnApply.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnApply.UseVisualStyleBackColor = false;
            this.btnApply.Click += new System.EventHandler(this.BtnApply_Click);
            // 
            // cboSort
            // 
            this.cboSort.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cboSort.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.cboSort.FormattingEnabled = true;
            this.cboSort.Location = new System.Drawing.Point(689, 46);
            this.cboSort.Name = "cboSort";
            this.cboSort.Size = new System.Drawing.Size(162, 31);
            this.cboSort.TabIndex = 1;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(605, 49);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(78, 23);
            this.label3.TabIndex = 0;
            this.label3.Text = "Soty by : ";
            // 
            // cboTo
            // 
            this.cboTo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cboTo.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.cboTo.FormattingEnabled = true;
            this.cboTo.Location = new System.Drawing.Point(425, 43);
            this.cboTo.Name = "cboTo";
            this.cboTo.Size = new System.Drawing.Size(162, 31);
            this.cboTo.TabIndex = 1;
            // 
            // cboFrom
            // 
            this.cboFrom.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cboFrom.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.cboFrom.FormattingEnabled = true;
            this.cboFrom.Location = new System.Drawing.Point(116, 40);
            this.cboFrom.Name = "cboFrom";
            this.cboFrom.Size = new System.Drawing.Size(162, 31);
            this.cboFrom.TabIndex = 1;
            // 
            // dgSchedule
            // 
            this.dgSchedule.AllowUserToAddRows = false;
            this.dgSchedule.AllowUserToDeleteRows = false;
            this.dgSchedule.AllowUserToResizeColumns = false;
            this.dgSchedule.AllowUserToResizeRows = false;
            this.dgSchedule.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgSchedule.BackgroundColor = System.Drawing.Color.White;
            this.dgSchedule.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgSchedule.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.dgSchedule.Location = new System.Drawing.Point(12, 160);
            this.dgSchedule.MultiSelect = false;
            this.dgSchedule.Name = "dgSchedule";
            this.dgSchedule.RowHeadersVisible = false;
            this.dgSchedule.RowTemplate.Height = 23;
            this.dgSchedule.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgSchedule.Size = new System.Drawing.Size(857, 307);
            this.dgSchedule.TabIndex = 1;
            this.dgSchedule.CellPainting += new System.Windows.Forms.DataGridViewCellPaintingEventHandler(this.DgSchedule_CellPainting);
            this.dgSchedule.SelectionChanged += new System.EventHandler(this.DgSchedule_SelectionChanged);
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "cancel.png");
            this.imageList1.Images.SetKeyName(1, "confirm.png");
            this.imageList1.Images.SetKeyName(2, "edit.png");
            this.imageList1.Images.SetKeyName(3, "import.png");
            // 
            // btnCancel
            // 
            this.btnCancel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(250)))), ((int)(((byte)(203)))));
            this.btnCancel.Font = new System.Drawing.Font("TeXGyreAdventor", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnCancel.ForeColor = System.Drawing.Color.Red;
            this.btnCancel.ImageIndex = 0;
            this.btnCancel.ImageList = this.imageList1;
            this.btnCancel.Location = new System.Drawing.Point(12, 511);
            this.btnCancel.Name = "btnCancel";
            this.btnCancel.Size = new System.Drawing.Size(171, 40);
            this.btnCancel.TabIndex = 7;
            this.btnCancel.Text = "Cancel Flight";
            this.btnCancel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnCancel.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnCancel.UseVisualStyleBackColor = false;
            this.btnCancel.Click += new System.EventHandler(this.BtnCancel_Click);
            // 
            // btnImport
            // 
            this.btnImport.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(25)))), ((int)(((byte)(106)))), ((int)(((byte)(166)))));
            this.btnImport.Font = new System.Drawing.Font("TeXGyreAdventor", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnImport.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(250)))), ((int)(((byte)(203)))));
            this.btnImport.ImageIndex = 3;
            this.btnImport.ImageList = this.imageList1;
            this.btnImport.Location = new System.Drawing.Point(682, 512);
            this.btnImport.Name = "btnImport";
            this.btnImport.Size = new System.Drawing.Size(187, 40);
            this.btnImport.TabIndex = 2;
            this.btnImport.Text = "Import Changes";
            this.btnImport.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnImport.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnImport.UseVisualStyleBackColor = false;
            this.btnImport.Click += new System.EventHandler(this.BtnImport_Click);
            // 
            // btnEdit
            // 
            this.btnEdit.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(25)))), ((int)(((byte)(106)))), ((int)(((byte)(166)))));
            this.btnEdit.Font = new System.Drawing.Font("TeXGyreAdventor", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnEdit.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(250)))), ((int)(((byte)(203)))));
            this.btnEdit.ImageIndex = 2;
            this.btnEdit.ImageList = this.imageList1;
            this.btnEdit.Location = new System.Drawing.Point(192, 511);
            this.btnEdit.Name = "btnEdit";
            this.btnEdit.Size = new System.Drawing.Size(135, 40);
            this.btnEdit.TabIndex = 2;
            this.btnEdit.Text = "Edit Flight";
            this.btnEdit.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnEdit.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnEdit.UseVisualStyleBackColor = false;
            this.btnEdit.Click += new System.EventHandler(this.BtnEdit_Click);
            // 
            // label5
            // 
            this.label5.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(247)))), ((int)(((byte)(148)))), ((int)(((byte)(32)))));
            this.label5.Location = new System.Drawing.Point(13, 478);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(20, 21);
            this.label5.TabIndex = 9;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(39, 478);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(143, 23);
            this.label4.TabIndex = 8;
            this.label4.Text = "Cancel Schedule";
            // 
            // FlightScheduleForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 23F);
            this.ClientSize = new System.Drawing.Size(878, 564);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.btnCancel);
            this.Controls.Add(this.dgSchedule);
            this.Controls.Add(this.btnImport);
            this.Controls.Add(this.btnEdit);
            this.Controls.Add(this.groupBox1);
            this.Name = "FlightScheduleForm";
            this.Text = "Manage Flight Schedules";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgSchedule)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private Common.UI.ComboBoxEx cboSort;
        private System.Windows.Forms.Label label3;
        private Common.UI.ComboBoxEx cboTo;
        private Common.UI.ComboBoxEx cboFrom;
        private System.Windows.Forms.Button btnApply;
        private Common.UI.DatePickerEx dtpOutbound;
        private System.Windows.Forms.TextBox txtFlight;
        private Common.UI.DataGrid dgSchedule;
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.Button btnEdit;
        private System.Windows.Forms.Button btnImport;
        private System.Windows.Forms.ImageList imageList1;
        private System.Windows.Forms.CheckBox chkFlight;
        private System.Windows.Forms.CheckBox chkOutbound;
        private System.Windows.Forms.CheckBox chkTo;
        private System.Windows.Forms.CheckBox chkFrom;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
    }
}
