namespace Session1.UI
{
    partial class MainMenuForm
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
            this.menuStrip = new System.Windows.Forms.MenuStrip();
            this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.lbWelcome = new System.Windows.Forms.Label();
            this.lbSpent = new System.Windows.Forms.Label();
            this.lbCrashes = new System.Windows.Forms.Label();
            this.dgLog = new Common.UI.DataGrid();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.menuStrip.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgLog)).BeginInit();
            this.SuspendLayout();
            // 
            // menuStrip
            // 
            this.menuStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.exitToolStripMenuItem});
            this.menuStrip.Location = new System.Drawing.Point(0, 0);
            this.menuStrip.Name = "menuStrip";
            this.menuStrip.Size = new System.Drawing.Size(889, 24);
            this.menuStrip.TabIndex = 0;
            this.menuStrip.Text = "menuStrip";
            // 
            // exitToolStripMenuItem
            // 
            this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
            this.exitToolStripMenuItem.Size = new System.Drawing.Size(38, 20);
            this.exitToolStripMenuItem.Text = "&Exit";
            this.exitToolStripMenuItem.Click += new System.EventHandler(this.ExitToolStripMenuItem_Click);
            // 
            // lbWelcome
            // 
            this.lbWelcome.AutoSize = true;
            this.lbWelcome.Location = new System.Drawing.Point(36, 50);
            this.lbWelcome.Name = "lbWelcome";
            this.lbWelcome.Size = new System.Drawing.Size(326, 23);
            this.lbWelcome.TabIndex = 1;
            this.lbWelcome.Text = "Hi, AAAAA, Welcome to AMONIC Airlines.";
            // 
            // lbSpent
            // 
            this.lbSpent.AutoSize = true;
            this.lbSpent.Location = new System.Drawing.Point(372, 100);
            this.lbSpent.Name = "lbSpent";
            this.lbSpent.Size = new System.Drawing.Size(244, 23);
            this.lbSpent.TabIndex = 1;
            this.lbSpent.Text = "Time spent on system : 00:19:03";
            // 
            // lbCrashes
            // 
            this.lbCrashes.AutoSize = true;
            this.lbCrashes.Location = new System.Drawing.Point(687, 100);
            this.lbCrashes.Name = "lbCrashes";
            this.lbCrashes.Size = new System.Drawing.Size(175, 23);
            this.lbCrashes.TabIndex = 1;
            this.lbCrashes.Text = "Number of crashes : 1";
            // 
            // dgLog
            // 
            this.dgLog.AllowUserToAddRows = false;
            this.dgLog.AllowUserToDeleteRows = false;
            this.dgLog.AllowUserToResizeColumns = false;
            this.dgLog.AllowUserToResizeRows = false;
            this.dgLog.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgLog.BackgroundColor = System.Drawing.Color.White;
            this.dgLog.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgLog.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.dgLog.Location = new System.Drawing.Point(25, 153);
            this.dgLog.MultiSelect = false;
            this.dgLog.Name = "dgLog";
            this.dgLog.RowHeadersVisible = false;
            this.dgLog.RowTemplate.Height = 23;
            this.dgLog.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgLog.Size = new System.Drawing.Size(836, 401);
            this.dgLog.TabIndex = 2;
            this.dgLog.CellPainting += new System.Windows.Forms.DataGridViewCellPaintingEventHandler(this.DgLog_CellPainting);
            // 
            // label5
            // 
            this.label5.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(247)))), ((int)(((byte)(148)))), ((int)(((byte)(32)))));
            this.label5.Location = new System.Drawing.Point(28, 567);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(20, 21);
            this.label5.TabIndex = 8;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(54, 567);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(87, 23);
            this.label4.TabIndex = 7;
            this.label4.Text = "Crash Log";
            // 
            // MainMenuForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 23F);
            this.ClientSize = new System.Drawing.Size(889, 600);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.dgLog);
            this.Controls.Add(this.lbCrashes);
            this.Controls.Add(this.lbSpent);
            this.Controls.Add(this.lbWelcome);
            this.Controls.Add(this.menuStrip);
            this.MainMenuStrip = this.menuStrip;
            this.Name = "MainMenuForm";
            this.Text = "AMONIC Airlines Automation System";
            this.menuStrip.ResumeLayout(false);
            this.menuStrip.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgLog)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
        private System.Windows.Forms.Label lbWelcome;
        private System.Windows.Forms.Label lbSpent;
        private System.Windows.Forms.Label lbCrashes;
        private Common.UI.DataGrid dgLog;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label4;
    }
}
