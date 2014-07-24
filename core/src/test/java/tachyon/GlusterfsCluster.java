/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tachyon;

import java.io.File;
import java.io.IOException;

import tachyon.conf.MasterConf;

public class GlusterfsCluster extends UnderFileSystemCluster {
  private String mMount = null;
  private String mVolume = null;
  private boolean mUseGfs = false;

  public GlusterfsCluster(String baseDir) {
    super(baseDir);
    mMount = MasterConf.get().getProperty("tachyon.underfs.glusterfs.mounts");
    mVolume = MasterConf.get().getProperty("tachyon.underfs.glusterfs.volumes");
    if (mMount != null && !mMount.equals("") && mVolume != null && !mVolume.equals("")) {
      if (new File(mMount).exists()) {
        mUseGfs = true;
      }
    }
  }

  @Override
  public String getUnderFilesystemAddress() {
    if (mUseGfs) {
      return new File(mMount + Constants.PATH_SEPARATOR + mBaseDir).getAbsolutePath();
    }
    // for env that doesn't have a glusterfs mount point, we fall to local fs
    return new File(mBaseDir).getAbsolutePath();
  }

  @Override
  public boolean isStarted() {
    return true;
  }

  @Override
  public void shutdown() throws IOException {
  }

  @Override
  public void start() throws IOException {
  }
}