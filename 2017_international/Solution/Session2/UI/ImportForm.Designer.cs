namespace Session2.UI
{
    partial class ImportForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ImportForm));
            this.label1 = new System.Windows.Forms.Label();
            this.txtPath = new System.Windows.Forms.TextBox();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.btnImport = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.lbApplied = new System.Windows.Forms.Label();
            this.lbDuplicated = new System.Windows.Forms.Label();
            this.lbMIssing = new System.Windows.Forms.Label();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(18, 20);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(329, 23);
            this.label1.TabIndex = 0;
            this.label1.Text = "Please select the text file with the changes";
            // 
            // txtPath
            // 
            this.txtPath.Location = new System.Drawing.Point(17, 55);
            this.txtPath.Name = "txtPath";
            this.txtPath.ReadOnly = true;
            this.txtPath.Size = new System.Drawing.Size(365, 31);
            this.txtPath.TabIndex = 1;
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "import.png");
            // 
            // btnImport
            // 
            this.btnImport.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(25)))), ((int)(((byte)(106)))), ((int)(((byte)(166)))));
            this.btnImport.Font = new System.Drawing.Font("TeXGyreAdventor", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnImport.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(250)))), ((int)(((byte)(203)))));
            this.btnImport.ImageIndex = 0;
            this.btnImport.ImageList = this.imageList1;
            this.btnImport.Location = new System.Drawing.Point(388, 49);
            this.btnImport.Name = "btnImport";
            this.btnImport.Size = new System.Drawing.Size(133, 40);
            this.btnImport.TabIndex = 3;
            this.btnImport.Text = "Import";
            this.btnImport.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnImport.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnImport.UseVisualStyleBackColor = false;
            this.btnImport.Click += new System.EventHandler(this.BtnImport_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.lbMIssing);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.lbDuplicated);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.lbApplied);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Location = new System.Drawing.Point(12, 103);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(508, 204);
            this.groupBox1.TabIndex = 4;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Results";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(21, 40);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(255, 23);
            this.label2.TabIndex = 1;
            this.label2.Text = "Successful Chanmges Applied : ";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(21, 103);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(246, 23);
            this.label3.TabIndex = 1;
            this.label3.Text = "Duplicate Records Discarded : ";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(21, 160);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(295, 23);
            this.label4.TabIndex = 1;
            this.label4.Text = "Record with missing fields discarded : ";
            // 
            // lbApplied
            // 
            this.lbApplied.Location = new System.Drawing.Point(372, 40);
            this.lbApplied.Name = "lbApplied";
            this.lbApplied.Size = new System.Drawing.Size(85, 20);
            this.lbApplied.TabIndex = 1;
            this.lbApplied.Text = "0";
            this.lbApplied.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // lbDuplicated
            // 
            this.lbDuplicated.Location = new System.Drawing.Point(372, 103);
            this.lbDuplicated.Name = "lbDuplicated";
            this.lbDuplicated.Size = new System.Drawing.Size(85, 20);
            this.lbDuplicated.TabIndex = 1;
            this.lbDuplicated.Text = "0";
            this.lbDuplicated.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // lbMIssing
            // 
            this.lbMIssing.Location = new System.Drawing.Point(372, 160);
            this.lbMIssing.Name = "lbMIssing";
            this.lbMIssing.Size = new System.Drawing.Size(85, 20);
            this.lbMIssing.TabIndex = 1;
            this.lbMIssing.Text = "0";
            this.lbMIssing.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.Filter = "csv files|*.csv";
            // 
            // ImportForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 23F);
            this.ClientSize = new System.Drawing.Size(528, 319);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.btnImport);
            this.Controls.Add(this.txtPath);
            this.Controls.Add(this.label1);
            this.Name = "ImportForm";
            this.Text = "Apply Schedule Changes";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtPath;
        private System.Windows.Forms.ImageList imageList1;
        private System.Windows.Forms.Button btnImport;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label lbMIssing;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label lbDuplicated;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label lbApplied;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
    }
}
