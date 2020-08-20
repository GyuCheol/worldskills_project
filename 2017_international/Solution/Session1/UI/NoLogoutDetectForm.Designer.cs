namespace Session1.UI
{
    partial class NoLogoutDetectForm
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
            this.lbTime = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.txtReason = new System.Windows.Forms.TextBox();
            this.rdoSoftware = new System.Windows.Forms.RadioButton();
            this.rdoSystem = new System.Windows.Forms.RadioButton();
            this.btnConfirm = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // lbTime
            // 
            this.lbTime.AutoSize = true;
            this.lbTime.Location = new System.Drawing.Point(12, 9);
            this.lbTime.Name = "lbTime";
            this.lbTime.Size = new System.Drawing.Size(471, 23);
            this.lbTime.TabIndex = 2;
            this.lbTime.Text = "No logout detected for your last login on 06/07/2017 at 08:22";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 43);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(75, 23);
            this.label2.TabIndex = 2;
            this.label2.Text = "Reason :";
            // 
            // txtReason
            // 
            this.txtReason.Location = new System.Drawing.Point(16, 71);
            this.txtReason.Multiline = true;
            this.txtReason.Name = "txtReason";
            this.txtReason.Size = new System.Drawing.Size(498, 365);
            this.txtReason.TabIndex = 3;
            // 
            // rdoSoftware
            // 
            this.rdoSoftware.AutoSize = true;
            this.rdoSoftware.Checked = true;
            this.rdoSoftware.Location = new System.Drawing.Point(16, 448);
            this.rdoSoftware.Name = "rdoSoftware";
            this.rdoSoftware.Size = new System.Drawing.Size(145, 27);
            this.rdoSoftware.TabIndex = 4;
            this.rdoSoftware.TabStop = true;
            this.rdoSoftware.Text = "Software Crash";
            this.rdoSoftware.UseVisualStyleBackColor = true;
            // 
            // rdoSystem
            // 
            this.rdoSystem.AutoSize = true;
            this.rdoSystem.Location = new System.Drawing.Point(187, 448);
            this.rdoSystem.Name = "rdoSystem";
            this.rdoSystem.Size = new System.Drawing.Size(130, 27);
            this.rdoSystem.TabIndex = 4;
            this.rdoSystem.Text = "System Crash";
            this.rdoSystem.UseVisualStyleBackColor = true;
            // 
            // btnConfirm
            // 
            this.btnConfirm.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(25)))), ((int)(((byte)(106)))), ((int)(((byte)(166)))));
            this.btnConfirm.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(250)))), ((int)(((byte)(203)))));
            this.btnConfirm.Location = new System.Drawing.Point(382, 442);
            this.btnConfirm.Name = "btnConfirm";
            this.btnConfirm.Size = new System.Drawing.Size(132, 39);
            this.btnConfirm.TabIndex = 5;
            this.btnConfirm.Text = "Confirm";
            this.btnConfirm.UseVisualStyleBackColor = false;
            // 
            // NoLogoutDetectForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 23F);
            this.ClientSize = new System.Drawing.Size(526, 488);
            this.Controls.Add(this.btnConfirm);
            this.Controls.Add(this.rdoSystem);
            this.Controls.Add(this.rdoSoftware);
            this.Controls.Add(this.txtReason);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.lbTime);
            this.Name = "NoLogoutDetectForm";
            this.Text = "No logout detected";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lbTime;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtReason;
        private System.Windows.Forms.RadioButton rdoSoftware;
        private System.Windows.Forms.RadioButton rdoSystem;
        private System.Windows.Forms.Button btnConfirm;
    }
}
