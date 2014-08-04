/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.remoteServer.impl.module;

import com.intellij.icons.AllIcons;
import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleBuilderListener;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.remoteServer.configuration.RemoteServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


public class CloudModuleBuilder extends JavaModuleBuilder {

  private RemoteServer<?> myAccount;
  private CloudApplicationConfiguration myApplicationConfiguration;

  public CloudModuleBuilder() {
    addListener(new ModuleBuilderListener() {

      @Override
      public void moduleCreated(@NotNull Module module) {
        configureModule(module);
      }
    });
  }

  public String getBuilderId() {
    return getClass().getName();
  }

  @Override
  public Icon getBigIcon() {
    return AllIcons.General.Balloon;
  }

  @Override
  public Icon getNodeIcon() {
    return AllIcons.General.Balloon;
  }

  @Override
  public String getDescription() {
    return "Java module of PAAS cloud application";
  }

  @Override
  public String getPresentableName() {
    return "Clouds";
  }

  @Override
  public String getGroupName() {
    return "Clouds";
  }

  @Override
  public String getParentGroup() {
    return JavaModuleType.JAVA_GROUP;
  }

  @Override
  public int getWeight() {
    return 30;
  }

  @Override
  public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
    return ModuleWizardStep.EMPTY_ARRAY;
  }

  @Nullable
  @Override
  public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
    return new CloudModuleWizardStep(this, context.getProject(), parentDisposable);
  }

  public void setAccount(RemoteServer<?> account) {
    myAccount = account;
  }

  public void setApplicationConfiguration(CloudApplicationConfiguration applicationConfiguration) {
    myApplicationConfiguration = applicationConfiguration;
  }

  private void configureModule(final Module module) {
    CloudModuleBuilderContribution
      .getInstanceByType(myAccount.getType()).configureModule(module, myAccount, myApplicationConfiguration, getContentEntryPath());
  }
}
